package com.jmdomingueza.telefonoip.sip.sip;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AckTest.class, ByeTest.class, CancelTest.class, InfoTest.class, InviteAuthenticateTest.class,
		InviteTest.class, MessageTest.class, NotifyTest.class, OptionsTest.class, PrackTest.class, PublishTest.class,
		ReferTest.class, RegisterAuthenticateTest.class, RegisterTest.class, SubscribeTest.class, UpdateTest.class })
public class RequestFactoryTestSuit {

}
