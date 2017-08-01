package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import java.util.Vector;

import javax.media.Codec;
import javax.media.Format;
import javax.media.Owned;
import javax.media.PlugIn;
import javax.media.Renderer;
import javax.media.Track;
import javax.media.control.FrameRateControl;

import com.jmdomingueza.telefonoip.rtp.beans.Audio;
import com.sun.media.BasicFilterModule;
import com.sun.media.BasicModule;
import com.sun.media.BasicPlayer;
import com.sun.media.BasicRendererModule;
import com.sun.media.BasicSinkModule;
import com.sun.media.InputConnector;
import com.sun.media.Log;
import com.sun.media.OutputConnector;
import com.sun.media.PlaybackEngine;
import com.sun.media.controls.ProgressControl;
import com.sun.media.renderer.audio.AudioRenderer;

public class MiPlaybackEngine extends PlaybackEngine {

	static boolean DEBUG = true;

	private Audio audio;
	private boolean useMoreRenderBuffer = false;

	public MiPlaybackEngine(BasicPlayer p, Audio audio) {
		super(p);
		this.audio = audio;
	}

	@Override
	protected boolean doConfigure() {
		if (!doConfigure1()) {
			return false;
		}

		String[] names = this.source.getOutputConnectorNames();
		this.trackControls = new MiBasicTrackControl[this.tracks.length];
		for (int i = 0; i < this.tracks.length; i++) {
			this.trackControls[i] = new PlayerTControl(this, this.tracks[i], this.source.getOutputConnector(names[i]));
		}

		return doConfigure2();
	}

	protected GraphNode buildTrackFromGraph(MiBasicTrackControl tc, GraphNode node) {
		BasicModule src = null;
		BasicModule dst = null;
		InputConnector ic = null;
		OutputConnector oc = null;
		boolean lastNode = true;
		Vector<PlugIn> used = new Vector<>(5);
		int indent = 0;

		if (node.plugin == null) {
			return null;
		}

		Log.setIndent(indent++);

		while ((node != null) && (node.plugin != null)) {
			if ((src = createModule(node, used)) == null) {
				Log.error("Internal error: buildTrackFromGraph");
				node.failed = true;
				return node;
			}

			if (lastNode) {
				if ((src instanceof BasicRendererModule)) {
					tc.setRendererModule((BasicRendererModule) src);

					if ((this.useMoreRenderBuffer)
							&& ((tc.getRendererModule().getRenderer() instanceof AudioRenderer))) {
						this.setRenderBufferSize(tc.getRendererModule().getRenderer());
					}
				} else if ((src instanceof BasicFilterModule)) {
					tc.setLastOC(src.getOutputConnector(null));
					tc.getLastOC().setFormat(node.output);
				}
				lastNode = false;
			}

			ic = src.getInputConnector(null);
			ic.setFormat(node.input);

			if (dst != null) {
				oc = tc.getLastOC();
				ic = dst.getInputConnector(null);
				oc.setFormat(ic.getFormat());
			}

			src.setController(this);
			if (!src.doRealize()) {
				Log.setIndent(indent--);
				node.failed = true;
				return node;
			}

			if ((oc != null) && (ic != null)) {
				this.connectModules(oc, ic, dst);
			}
			dst = src;
			node = node.prev;
		}

		dst = src;
		while (true) {
			dst.setModuleListener(this);
			this.modules.addElement(dst);
			tc.getModules().addElement(dst);
			if ((dst instanceof BasicFilterModule))
				this.filters.addElement(dst);
			else if ((dst instanceof BasicSinkModule))
				this.sinks.addElement(dst);
			oc = dst.getOutputConnector(null);
			if ((oc == null) || ((ic = oc.getInputConnector()) == null))
				break;
			if ((dst = (BasicModule) ic.getModule()) != null) {
				continue;
			}

		}

		tc.getFirstOC().setFormat(tc.getOriginalFormat());

		ic = src.getInputConnector(null);
		Format fmt = ic.getFormat();
		if ((fmt == null) || (!fmt.equals(tc.getOriginalFormat()))) {
			ic.setFormat(tc.getOriginalFormat());
		}
		connectModules(tc.getFirstOC(), ic, src);

		Log.setIndent(indent--);

		return null;
	}

	protected BasicModule createModule(GraphNode n, Vector<PlugIn> used) {
		BasicModule m = null;

		if (n.plugin == null)
			return null;
		PlugIn p;

		p = new MiJavaSoundRenderer(this.audio);
		if (((n.type == -1) || (n.type == 4)) && ((p instanceof Renderer))) {
			m = new MiBasicRendererModule((Renderer) p);
		} else if (((n.type == -1) || (n.type == 2)) && ((p instanceof Codec))) {
			m = new BasicFilterModule((Codec) p);
		}

		if ((DEBUG) && (m != null))
			m.setJMD(this.jmd);

		return m;
	}

	class PlayerGraphBuilder extends MiSimpleGraphBuilder {
		protected MiPlaybackEngine engine;

		PlayerGraphBuilder(MiPlaybackEngine engine) {
			this.engine = engine;
		}

		protected GraphNode buildTrackFromGraph(MiBasicTrackControl tc, GraphNode node) {
			return this.engine.buildTrackFromGraph((PlayerTControl) tc, node);
		}

	}

	class PlayerTControl extends MiBasicTrackControl implements Owned {

		protected PlayerGraphBuilder gb;

		public PlayerTControl(MiPlaybackEngine engine, Track track, OutputConnector oc) {
			super(engine, track, oc);
		}

		public Object getOwner() {
			return MiPlaybackEngine.this.player;
		}

		public boolean buildTrack(int trackID, int numTracks) {
			if (this.gb == null)
				this.gb = new PlayerGraphBuilder(MiPlaybackEngine.this);
			else
				this.gb.reset();
			boolean rtn = this.gb.buildGraph(this);

			this.gb = null;

			return rtn;
		}

		public boolean isTimeBase() {
			for (int j = 0; j < this.modules.size(); j++) {
				if (this.modules.elementAt(j) == MiPlaybackEngine.this.masterSink)
					return true;
			}
			return false;
		}

		protected ProgressControl progressControl() {
			return MiPlaybackEngine.this.progressControl;
		}

		protected FrameRateControl frameRateControl() {
			return MiPlaybackEngine.this.frameRateControl;
		}
	}

}