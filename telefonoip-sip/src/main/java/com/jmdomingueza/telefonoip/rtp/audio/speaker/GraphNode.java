package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import javax.media.Codec;
import javax.media.Format;
import javax.media.Multiplexer;
import javax.media.PlugIn;
import javax.media.Renderer;

class GraphNode {
	Class<?> clz;
	String cname;
	PlugIn plugin;
	int type = -1;
	Format input;
	Format output = null;
	Format[] supportedIns;
	Format[] supportedOuts;
	GraphNode prev;
	int level;
	boolean failed = false;
	boolean custom = false;

	static final int ARRAY_INC = 30;

	int attemptedIdx = 0;
	Format[] attempted = null;

	GraphNode(PlugIn plugin, Format input, GraphNode prev, int level) {
		this(plugin == null ? null : plugin.getClass().getName(), plugin, input, prev, level);
	}

	GraphNode(String cname, PlugIn plugin, Format input, GraphNode prev, int level) {
		this.cname = cname;
		this.plugin = plugin;
		this.input = input;
		this.prev = prev;
		this.level = level;
	}

	GraphNode(GraphNode gn, Format input, GraphNode prev, int level) {
		this.cname = gn.cname;
		this.plugin = gn.plugin;
		this.type = gn.type;
		this.custom = gn.custom;
		this.input = input;
		this.prev = prev;
		this.level = level;
		this.supportedIns = gn.supportedIns;
		if (gn.input == input)
			this.supportedOuts = gn.supportedOuts;
	}

	Format[] getSupportedInputs() {
		if (this.supportedIns != null)
			return this.supportedIns;
		if (this.plugin == null)
			return null;
		if (((this.type == -1) || (this.type == 2)) && ((this.plugin instanceof Codec))) {
			this.supportedIns = ((Codec) this.plugin).getSupportedInputFormats();
		} else if (((this.type == -1) || (this.type == 4)) && ((this.plugin instanceof Renderer))) {
			this.supportedIns = ((Renderer) this.plugin).getSupportedInputFormats();
		} else if ((this.plugin instanceof Multiplexer))
			this.supportedIns = ((Multiplexer) this.plugin).getSupportedInputFormats();
		return this.supportedIns;
	}

	Format[] getSupportedOutputs(Format in) {
		if ((in == this.input) && (this.supportedOuts != null))
			return this.supportedOuts;
		if (this.plugin == null)
			return null;
		if (((this.type == -1) || (this.type == 4)) && ((this.plugin instanceof Renderer))) {
			return null;
		}
		if (((this.type == -1) || (this.type == 2)) && ((this.plugin instanceof Codec))) {
			Format[] outs = ((Codec) this.plugin).getSupportedOutputFormats(in);
			if (this.input == in)
				this.supportedOuts = outs;
			return outs;
		}
		return null;
	}

	public void resetAttempted() {
		this.attemptedIdx = 0;
		this.attempted = null;
	}

	boolean checkAttempted(Format input) {
		if (this.attempted == null) {
			this.attempted = new Format[ARRAY_INC];
			this.attempted[(this.attemptedIdx++)] = input;
			return false;
		}

		for (int j = 0; j < this.attemptedIdx; j++) {
			if (input.equals(this.attempted[j])) {
				return true;
			}
		}

		if (this.attemptedIdx >= this.attempted.length) {
			Format[] newarray = new Format[this.attempted.length + ARRAY_INC];
			System.arraycopy(this.attempted, 0, newarray, 0, this.attempted.length);
			this.attempted = newarray;
		}
		this.attempted[(this.attemptedIdx++)] = input;
		return false;
	}
}
