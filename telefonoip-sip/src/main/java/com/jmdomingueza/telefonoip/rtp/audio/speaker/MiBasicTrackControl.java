package com.jmdomingueza.telefonoip.rtp.audio.speaker;

import java.util.Vector;

import javax.media.Track;

import com.sun.media.BasicModule;
import com.sun.media.BasicRendererModule;
import com.sun.media.BasicTrackControl;
import com.sun.media.OutputConnector;


public class MiBasicTrackControl extends BasicTrackControl {

	OutputConnector miFirstOC;
	OutputConnector miLastOC;

	public MiBasicTrackControl(MiPlaybackEngine engine, Track track, OutputConnector oc) {
		super(engine, track, null);
		miFirstOC = oc;
		miLastOC = oc;

	}

	public BasicRendererModule getRendererModule() {
		return this.rendererModule;
	}

	public boolean getPrefetchFailed() {
		return this.prefetchFailed;
	}

	public boolean getRendererFailed() {
		return this.rendererFailed;
	}

	public void setRendererModule(BasicRendererModule src) {
		this.rendererModule = src;
	}

	public Vector<BasicModule> getModules() {
		return this.modules;
	}

	public OutputConnector getFirstOC(){
		return miFirstOC;
	}

	public OutputConnector getLastOC(){
		return miLastOC;
	}

	public void setLastOC(OutputConnector outputConnector) {
		miLastOC = outputConnector;

	}
}
