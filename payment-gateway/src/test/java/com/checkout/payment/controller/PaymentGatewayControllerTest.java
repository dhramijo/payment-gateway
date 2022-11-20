package com.checkout.payment.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.services.PaymentGatewayService;
import com.checkout.payment.utils.ResourceFileLoaderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class PaymentGatewayControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private PaymentGatewayService paymentGatewayService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testRetrievePaymentDetails() throws Exception {

    mvc.perform(get("/api/v1/payments/pay_1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(paymentGatewayService, times(1))
        .retrievePaymentDetails(isA(String.class));

  }

  @Test
  void testCreateNewPaymentRequest() throws Exception {
    var paymentRequestDetails = (PaymentRequestDetails) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CreateNewPaymentRequestPayload.json", PaymentRequestDetails.class);

    mvc.perform(post("/api/v1/payments")
            .content(objectMapper.writeValueAsString(paymentRequestDetails))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
           .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(paymentGatewayService, times(1))
        .generatePaymentRequest(isA(PaymentRequestDetails.class));
  }
}