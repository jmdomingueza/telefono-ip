package com.jmdomingueza.telefonoip.dummy.services;

import com.jmdomingueza.telefonoip.dummy.beans.Call;
import com.jmdomingueza.telefonoip.dummy.beans.Count;

public interface DummyService {
	
	public void registerCount(Count count);
	
	public void unregisterCount(Count count);
	
	public void talkingCall(Call call);
	
	public void terminatedCall(Call call);

	public void canceledCall(Call call);

	public void heldCall(Call call);

	public void transferedCall(Call call);

	public void conferenceCall(Call call);

}
