package com.fdmgroup.buythethingsisell.springcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.User;
import com.fdmgroup.buythethingsisell.jdbc.userfunctions.RegisterNewUser;

public class LoginPageTest {
	
	@InjectMocks private LoginPage loginPage;
	@Mock private RegisterNewUser mockRegisterNewUser;
	@Mock private User mockUser;
	@Mock private Model mockModel;
	@Mock private ModelAndView mockModelAndView;
	@Mock private SecurityContextUserName mockSecurityContextUserName;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockUser.getPasswordHashed()).thenReturn("hashed");
		when(mockRegisterNewUser.registerNewUser(mockUser)).thenReturn(true);
		when(mockSecurityContextUserName.getUserName()).thenReturn("email1");
	}
	
	@Test
	public void test_login_goToLoginPage_whenCalled(){
		loginPage.login(mockModelAndView, null, null);
	}
	
	@Test
	public void test_login_goToLoginPage_whenCalledWithErrorsAndLogOut(){
		loginPage.login(mockModelAndView, "error", "logout");
	}
	
	@Test
	public void test_accessDenied(){
		loginPage.accesssDenied(mockModelAndView);
		verify(mockModelAndView).addObject("username", "email1");
	}
	
	@Test
	public void test_getRegistrationPage(){
		loginPage.getRegistrationPage(mockModel);
		verify(mockModel).addAttribute(anyString(), anyObject());
	}
	
	@Test
	public void test_registrationSubmit_ReturnsIndex_WhenUserSuccessfullyRegistered(){
		String actual = loginPage.registrationSubmit(mockUser, mockModel);
		String expected = "index";
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_registrationSubmit_ReturnsRegistrationAndBoolean_WhenUserUnsuccessfullyRegisters(){
		when(mockRegisterNewUser.registerNewUser(mockUser)).thenReturn(false);
		String actual = loginPage.registrationSubmit(mockUser, mockModel);
		String expected = "register";
		verify(mockModel).addAttribute(false);
		assertEquals(expected, actual);
	}
}
