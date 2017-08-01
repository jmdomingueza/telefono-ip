package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.media.Codec;
import javax.media.Format;
import javax.media.Multiplexer;
import javax.media.PlugIn;
import javax.media.PlugInManager;
import javax.media.Renderer;
import javax.media.ResourceUnavailableException;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;

import com.sun.media.BasicPlugIn;
import com.sun.media.GraphInspector;
import com.sun.media.Log;
import com.sun.media.codec.audio.mpa.DePacketizer;
import com.sun.media.codec.audio.mpa.Packetizer;

public class MiSimpleGraphBuilder {
	protected int STAGES = 4;
	protected Hashtable<String,GraphNode> plugIns = new Hashtable<>(40);
	protected GraphNode[] targetPlugins = null;
	protected Vector<?> targetPluginNames = null;
	protected int targetType = -1;
	int indent = 0;
	protected static GraphInspector inspector;

	public static void setGraphInspector(GraphInspector insp) {
		inspector = insp;
	}

	public void reset() {
		Enumeration<?> enumm = this.plugIns.elements();

		while (enumm.hasMoreElements()) {
			GraphNode n = (GraphNode) enumm.nextElement();
			n.resetAttempted();
		}
	}

	boolean buildGraph(MiBasicTrackControl tc) {
		Log.comment("Input: " + tc.getOriginalFormat());

		Vector<GraphNode> candidates = new Vector<>();
		GraphNode node = new GraphNode(null, (PlugIn) null, tc.getOriginalFormat(), null, 0);

		this.indent = 1;
		Log.setIndent(this.indent);

		if (!setDefaultTargets(tc.getOriginalFormat())) {
			return false;
		}
		candidates.addElement(node);

		while ((node = buildGraph(candidates)) != null) {
			GraphNode failed;
			if ((failed = buildTrackFromGraph(tc, node)) == null) {
				this.indent = 0;
				Log.setIndent(this.indent);
				return true;
			}

			removeFailure(candidates, failed, tc.getOriginalFormat());
		}

		this.indent = 0;
		Log.setIndent(this.indent);
		return false;
	}

	protected GraphNode buildTrackFromGraph(MiBasicTrackControl tc, GraphNode node) {
		return null;
	}

	GraphNode buildGraph(Format input) {
		Log.comment("Input: " + input);

		Vector<GraphNode> candidates = new Vector<>();
		GraphNode node = new GraphNode(null, (PlugIn) null, input, null, 0);

		this.indent = 1;
		Log.setIndent(this.indent);

		if (!setDefaultTargets(input)) {
			return null;
		}
		candidates.addElement(node);

		while ((node = buildGraph(candidates)) != null) {
			GraphNode failed;
			if ((failed = verifyGraph(node)) == null) {
				this.indent = 0;
				Log.setIndent(this.indent);
				return node;
			}

			removeFailure(candidates, failed, input);
		}

		this.indent = 0;
		Log.setIndent(this.indent);

		return node;
	}

	GraphNode buildGraph(Vector<GraphNode> candidates) {
		GraphNode node;
		while ((node = doBuildGraph(candidates)) == null) {
			if (candidates.isEmpty())
				break;
		}
		return node;
	}

	GraphNode doBuildGraph(Vector<GraphNode> candidates) {
		if (candidates.isEmpty()) {
			return null;
		}
		GraphNode node = (GraphNode) candidates.firstElement();
		candidates.removeElementAt(0);

		if ((node.input == null) && ((node.plugin == null) || (!(node.plugin instanceof Codec)))) {
			Log.error("Internal error: doBuildGraph");
			return null;
		}

		int oldIndent = this.indent;

		Log.setIndent(node.level + 1);

		if (node.plugin != null) {
			if (verifyInput(node.plugin, node.input) == null)
				return null;
		}
		GraphNode n;
		if ((n = findTarget(node)) != null) {
			this.indent = oldIndent;
			Log.setIndent(this.indent);
			return n;
		}

		if (node.level >= this.STAGES) {
			this.indent = oldIndent;
			Log.setIndent(this.indent);
			return null;
		}

		boolean mp3Pkt = false;
		Format[] outs;
		Format input;
		if (node.plugin != null) {
			if (node.output != null) {
				outs = new Format[1];
				outs[0] = node.output;
			} else {
				outs = node.getSupportedOutputs(node.input);
				if ((outs == null) || (outs.length == 0)) {
					this.indent = oldIndent;
					Log.setIndent(this.indent);
					return null;
				}
			}
			input = node.input;

			if ((node.plugin instanceof Packetizer)) {
				mp3Pkt = true;
			}
		} else {
			outs = new Format[1];
			outs[0] = node.input;
			input = null;
		}

		for (int i = 0; i < outs.length; i++) {
			if ((!node.custom) && (input != null) && (input.equals(outs[i]))) {
				continue;
			}
			if (node.plugin != null) {
				if (verifyOutput(node.plugin, outs[i]) == null) {
					if ((inspector != null) && (inspector.detailMode())) {
						inspector.verifyOutputFailed(node.plugin, outs[i]);
					}
				} else {
					if ((inspector != null) && (!inspector.verify((Codec) node.plugin, node.input, outs[i]))) {
						continue;
					}
				}
			} else {
				Vector<String> cnames = PlugInManager.getPlugInList(outs[i], null, 2);

				if ((cnames == null) || (cnames.size() == 0)) {
					continue;
				}
				for (int j = 0; j < cnames.size(); j++) {
					GraphNode gn;
					if ((gn = getPlugInNode((String) cnames.elementAt(j), 2, this.plugIns)) == null) {
						continue;
					}

					if ((mp3Pkt) && ((gn.plugin instanceof DePacketizer))) {
						continue;
					}

					if (gn.checkAttempted(outs[i])) {
						continue;
					}

					Format[] ins = gn.getSupportedInputs();
					Format fmt;
					if ((fmt = matches(outs[i], ins, null, gn.plugin)) == null) {
						if ((inspector != null) && (inspector.detailMode()))
							inspector.verifyInputFailed(gn.plugin, outs[i]);
					} else {
						if ((inspector != null) && (inspector.detailMode())
								&& (!inspector.verify((Codec) gn.plugin, fmt, null))) {
							continue;
						}
						n = new GraphNode(gn, fmt, node, node.level + 1);
						candidates.addElement(n);
					}

				}

			}

		}

		this.indent = oldIndent;
		Log.setIndent(this.indent);
		return null;
	}

	GraphNode findTarget(GraphNode node) {
		Format[] outs;
		if (node.plugin == null) {
			outs = new Format[1];
			outs[0] = node.input;
		} else if (node.output != null) {
			outs = new Format[1];
			outs[0] = node.output;
		} else {
			outs = node.getSupportedOutputs(node.input);
			if ((outs == null) || (outs.length == 0)) {
				return null;
			}
		}
		GraphNode n;
		if ((this.targetPlugins != null) && ((n = verifyTargetPlugins(node, outs)) != null)) {
			return n;
		}
		return null;
	}

	GraphNode verifyTargetPlugins(GraphNode node, Format[] outs) {
		for (int i = 0; i < this.targetPlugins.length; i++) {
			GraphNode gn;
			if ((gn = this.targetPlugins[i]) == null) {
				String name = (String) this.targetPluginNames.elementAt(i);
				if (name == null) {
					continue;
				}
				Format[] base = PlugInManager.getSupportedInputFormats(name, this.targetType);

				if (matches(outs, base, null, null) == null) {
					continue;
				}

				if ((gn = getPlugInNode(name, this.targetType, this.plugIns)) == null) {
					this.targetPluginNames.setElementAt(null, i);
				} else {
					this.targetPlugins[i] = gn;
				}
			} else {
				Format fmt;
				if ((fmt = matches(outs, gn.getSupportedInputs(), node.plugin, gn.plugin)) == null) {
					continue;
				}
				if (inspector != null) {
					if ((node.plugin != null) && (!inspector.verify((Codec) node.plugin, node.input, fmt))) {
						continue;
					}
					if (((gn.type == -1) || (gn.type == 2)) && ((gn.plugin instanceof Codec))
							? inspector.verify((Codec) gn.plugin, fmt, null)
							: ((gn.type == -1) || (gn.type == 4)) && ((gn.plugin instanceof Renderer))
									&& (!inspector.verify((Renderer) gn.plugin, fmt)))
						continue;
				} else {
					return new GraphNode(gn, fmt, node, node.level + 1);
				}
			}
		}
		return null;
	}

	boolean setDefaultTargets(Format in) {
		return setDefaultTargetRenderer(in);
	}

	boolean setDefaultTargetRenderer(Format in) {
		if ((in instanceof AudioFormat)) {
			this.targetPluginNames = PlugInManager
					.getPlugInList(new AudioFormat(null, -1.0D, -1, -1, -1, -1, -1, -1.0D, null), null, 4);
		} else if ((in instanceof VideoFormat)) {
			this.targetPluginNames = PlugInManager.getPlugInList(new VideoFormat(null, null, -1, null, -1.0F), null, 4);
		} else {
			this.targetPluginNames = PlugInManager.getPlugInList(null, null, 4);
		}

		if ((this.targetPluginNames == null) || (this.targetPluginNames.size() == 0)) {
			return false;
		}

		this.targetPlugins = new GraphNode[this.targetPluginNames.size()];
		this.targetType = 4;

		return true;
	}

	protected GraphNode verifyGraph(GraphNode node) {
		Format prevFormat = null;
		Vector<PlugIn> used = new Vector<>(5);

		if (node.plugin == null) {
			return null;
		}

		Log.setIndent(this.indent++);

		while ((node != null) && (node.plugin != null)) {
			if (used.contains(node.plugin)) {
				PlugIn p;
				if ((node.cname == null) || ((p = createPlugIn(node.cname, -1)) == null)) {
					Log.write("Failed to instantiate " + node.cname);
					return node;
				}
				node.plugin = p;
			} else {
				used.addElement(node.plugin);
			}

			if (((node.type == -1) || (node.type == 4)) && ((node.plugin instanceof Renderer))) {
				((Renderer) node.plugin).setInputFormat(node.input);
			} else if (((node.type == -1) || (node.type == 2)) && ((node.plugin instanceof Codec))) {
				((Codec) node.plugin).setInputFormat(node.input);
				if (prevFormat != null)
					((Codec) node.plugin).setOutputFormat(prevFormat);
				else if (node.output != null) {
					((Codec) node.plugin).setOutputFormat(node.output);
				}

			}

			if (((node.type != -1) && (node.type != 4)) || (!(node.plugin instanceof Renderer))) {
				try {
					node.plugin.open();
				} catch (Exception e) {
					Log.warning("Failed to open: " + node.plugin);
					node.failed = true;
					return node;
				}
			}

			prevFormat = node.input;
			node = node.prev;
		}

		Log.setIndent(this.indent--);

		return null;
	}

	void removeFailure(Vector<GraphNode> candidates, GraphNode failed, Format input) {
		if (failed.plugin == null) {
			return;
		}

		Log.comment("Failed to open plugin " + failed.plugin + ". Will re-build the graph allover again");
		candidates.removeAllElements();
		GraphNode hsyn = new GraphNode(null, (PlugIn) null, input, null, 0);

		this.indent = 1;
		Log.setIndent(this.indent);

		candidates.addElement(hsyn);

		failed.failed = true;
		this.plugIns.put(failed.plugin.getClass().getName(), failed);

		Enumeration<String> e = this.plugIns.keys();
		while (e.hasMoreElements()) {
			String ss = (String) e.nextElement();
			GraphNode nn = (GraphNode) this.plugIns.get(ss);
			if (!nn.failed)
				this.plugIns.remove(ss);
		}
	}

	public static GraphNode getPlugInNode(String name, int type, Hashtable<String,GraphNode> plugIns) {
		GraphNode gn = null;

		if ((plugIns == null) || ((gn = (GraphNode) plugIns.get(name)) == null)) {
			PlugIn p = createPlugIn(name, type);

			gn = new GraphNode(name, p, null, null, 0);
			if (plugIns != null) {
				plugIns.put(name, gn);
			}
			if (p == null) {
				gn.failed = true;
				return null;
			}
			return gn;
		}

		if (gn.failed) {
			return null;
		}
		if (verifyClass(gn.plugin, type)) {
			return gn;
		}
		return null;
	}

	public static Codec findCodec(Format in, Format out, Format[] selectedIn, Format[] selectedOut) {
		Vector<String> cnames = PlugInManager.getPlugInList(in, out, 2);

		if (cnames == null) {
			return null;
		}

		Codec c = null;

		for (int i = 0; i < cnames.size(); i++) {
			if ((c = (Codec) createPlugIn((String) cnames.elementAt(i), 2)) == null) {
				continue;
			}
			Format[] fmts = c.getSupportedInputFormats();
			Format matched;
			if ((matched = matches(in, fmts, null, c)) == null)
				continue;
			if ((selectedIn != null) && (selectedIn.length > 0))
				selectedIn[0] = matched;
			fmts = c.getSupportedOutputFormats(matched);
			if ((fmts == null) || (fmts.length == 0)) {
				continue;
			}
			boolean success = false;
			for (int j = 0; j < fmts.length; j++) {
				if (out != null) {
					if (!out.matches(fmts[j]))
						continue;
					if ((matched = out.intersects(fmts[j])) == null) {
						continue;
					}
				} else {
					matched = fmts[j];
				}
				if (c.setOutputFormat(matched) != null) {
					success = true;
					break;
				}
			}
			if (!success)
				continue;
			try {
				c.open();
			} catch (ResourceUnavailableException e) {
			}
			if ((selectedOut != null) && (selectedOut.length > 0)) {
				selectedOut[0] = matched;
			}
			return c;
		}

		return null;
	}

	public static Renderer findRenderer(Format in) {
		Vector<String> names = PlugInManager.getPlugInList(in, null, 4);

		if (names == null) {
			return null;
		}

		Renderer r = null;

		for (int i = 0; i < names.size(); i++) {
			if ((r = (Renderer) createPlugIn((String) names.elementAt(i), 4)) == null) {
				continue;
			}
			Format[] fmts = r.getSupportedInputFormats();
			if (matches(in, fmts, null, r) == null)
				continue;
			try {
				r.open();
			} catch (ResourceUnavailableException e) {
			}
			return r;
		}

		return null;
	}

	public static Vector<PlugIn> findRenderingChain(Format in, Vector<Format> formats) {
		MiSimpleGraphBuilder gb = new MiSimpleGraphBuilder();
		GraphNode n;
		if ((n = gb.buildGraph(in)) == null) {
			return null;
		}
		Vector<PlugIn> list = new Vector<>(10);

		while ((n != null) && (n.plugin != null)) {
			list.addElement(n.plugin);
			if (formats != null)
				formats.addElement(n.input);
			n = n.prev;
		}

		return list;
	}

	public static PlugIn createPlugIn(String name, int type) {
		Object obj;
		try {
			Class<?> cls = BasicPlugIn.getClassForName(name);
			obj = cls.newInstance();
		} catch (Exception e) {
			return null;
		} catch (Error e) {
			return null;
		}

		if (verifyClass(obj, type)) {
			return (PlugIn) obj;
		}
		return null;
	}

	public static boolean verifyClass(Object obj, int type) {
		Class<?> cls;
		switch (type) {
		case 2:
			cls = Codec.class;
			break;
		case 4:
			cls = Renderer.class;
			break;
		case 5:
			cls = Multiplexer.class;
			break;
		case 3:
		default:
			cls = PlugIn.class;
		}

		return cls.isInstance(obj);
	}

	public static Format matches(Format[] outs, Format[] ins, PlugIn up, PlugIn down) {
		if (outs == null)
			return null;
		for (int i = 0; i < outs.length; i++) {
			Format fmt;
			if ((fmt = matches(outs[i], ins, up, down)) != null)
				return fmt;
		}
		return null;
	}

	public static Format matches(Format out, Format[] ins, PlugIn up, PlugIn down) {
		if ((out == null) || (ins == null))
			return null;
		for (int i = 0; i < ins.length; i++) {
			if ((ins[i] == null) || (!ins[i].getClass().isAssignableFrom(out.getClass())) || (!out.matches(ins[i]))) {
				continue;
			}
			Format fmt = out.intersects(ins[i]);

			if (fmt == null) {
				continue;
			}

			if ((down != null) && ((fmt = verifyInput(down, fmt)) == null)) {
				continue;
			}
			Format refined = fmt;
			if ((up != null) && ((refined = verifyOutput(up, fmt)) == null)) {
				continue;
			}

			if ((down == null) || (refined == fmt) || (verifyInput(down, refined) != null)) {
				return refined;
			}
		}
		return null;
	}

	public static Format matches(Format[] outs, Format in, PlugIn up, PlugIn down) {
		Format[] ins = new Format[1];
		ins[0] = in;
		return matches(outs, ins, up, down);
	}

	public static Format verifyInput(PlugIn p, Format in) {
		if ((p instanceof Codec))
			return ((Codec) p).setInputFormat(in);
		if ((p instanceof Renderer))
			return ((Renderer) p).setInputFormat(in);
		return null;
	}

	public static Format verifyOutput(PlugIn p, Format out) {
		if ((p instanceof Codec))
			return ((Codec) p).setOutputFormat(out);
		return null;
	}
}
