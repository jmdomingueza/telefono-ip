package com.jmdomimgueza.telefonoip.negocio.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jmdomingueza.telefonoip.common.ctx.CTX;
import com.jmdomingueza.telefonoip.negocio.beans.CallSip;
import com.jmdomingueza.telefonoip.negocio.beans.CountSip;
import com.jmdomingueza.telefonoip.negocio.factories.BeanFactory;
import com.jmdomingueza.telefonoip.negocio.services.CountSipService;
import com.jmdomingueza.telefonoip.negocio.services.CallSipService;


/**
 * Unit test for SettingsService.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TelephonySipServiceTest {

	private static  CallSipService telephonySipService;
	private static  CountSipService countSipService;
	
	@BeforeClass
	public static void beforeClass(){
		CTX.ctx = new ClassPathXmlApplicationContext("application-context.xml");

		telephonySipService = (CallSipService) CTX.ctx.getBean("telephonyService");
		//telephonySipService.init();
		countSipService = (CountSipService) CTX.ctx.getBean("countSipService");
		
	}

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
		//telephonySipService.destroy();
		telephonySipService = null;
		//countSipService.destroy();
		countSipService = null;
	}
	
	
	/**
	 * Test que registra un usuario correctamente.
	 * @throws Exception 
	 */
	@Test
	public void registroOK() throws Exception {
		CountSip countSip;
		List<Integer> audioAvalibleList;
		
		audioAvalibleList = new ArrayList<>();
		audioAvalibleList.add(8);
		audioAvalibleList.add(101);
		countSip = BeanFactory.createCountSip("1001",null,"173.25.19.14",CountSip.State.unregistered, "1001",audioAvalibleList);
		countSipService.createCountSip(countSip);
		Assert.assertEquals(CountSip.State.registered, countSip.getState());
		
		
	}
	
	/**
	 * Test que registra y desregistra correctamente.
	 * @throws Exception 
	 */
	@Test
	public void desregistroOK() throws Exception {
		CountSip countSip;
		List<Integer> audioAvalibleList;
		
		audioAvalibleList = new ArrayList<>();
		audioAvalibleList.add(8);
		audioAvalibleList.add(101);
		countSip = BeanFactory.createCountSip("1001",null,"173.25.19.14",CountSip.State.unregistered, "1001",audioAvalibleList);
		countSipService.createCountSip(countSip);
		Assert.assertEquals(CountSip.State.registered, countSip.getState());
		Thread.sleep(10000);
		
		
		countSipService.removeCountSip(countSip);
		Assert.assertEquals(CountSip.State.unregistered, countSip.getState());
		
	}

	/**
	 * Test que realiza una llamada correctamente
	 * @throws Exception 
	 */
	@Test
	public void llamadaSalienteOK() throws Exception {
		CountSip countSip;
		CallSip callSip;
		List<Integer> audioAvalibleList;
		
		audioAvalibleList = new ArrayList<>();
		audioAvalibleList.add(8);
		audioAvalibleList.add(101);
		countSip = BeanFactory.createCountSip("1001",null,"173.25.19.14",CountSip.State.unregistered, "1001",audioAvalibleList);
		countSipService.createCountSip(countSip);
		Thread.sleep(10000);
		Assert.assertEquals(CountSip.State.registered, countSip.getState());
		
		callSip = BeanFactory.createCallSip(1,countSip,"1002",CallSip.State.dialing,CallSip.Direction.out,"Texto",null,null,null,"application","sdp",0);
		telephonySipService.dial(countSip,"1001");
		Thread.sleep(10000);
		Assert.assertEquals(CallSip.State.talking, callSip.getState());
		
		
		
		telephonySipService.drop(callSip);
		Thread.sleep(10000);
		Assert.assertEquals(CallSip.State.terminated, callSip.getState());
		
		countSipService.removeCountSip(countSip);
		Assert.assertEquals(CountSip.State.unregistered, countSip.getState());
		
	}
	
	/**
	 * Test que realiza una llamada correctamente
	 * @throws Exception 
	 */
	@Test
	public void llamadaSalienteError() throws Exception {
		CountSip countSip;
		CallSip callSip;
		List<Integer> audioAvalibleList;
		
		audioAvalibleList = new ArrayList<>();
		audioAvalibleList.add(8);
		audioAvalibleList.add(101);
		countSip = BeanFactory.createCountSip("1001",null,"173.25.19.14",CountSip.State.unregistered, "1001",audioAvalibleList);
		countSipService.createCountSip(countSip);
		Thread.sleep(10000);
		Assert.assertEquals(CountSip.State.registered, countSip.getState());
		
		
		callSip = BeanFactory.createCallSip(1,countSip,"1002",CallSip.State.dialing,CallSip.Direction.out,"Texto",null,null,null,"application","sdp",0);
		telephonySipService.dial(countSip,"1001");
		
		Assert.assertEquals(CallSip.State.error, callSip.getState());
		
		countSipService.removeCountSip(countSip);
		Assert.assertEquals(CountSip.State.unregistered, countSip.getState());
		
	}

}
