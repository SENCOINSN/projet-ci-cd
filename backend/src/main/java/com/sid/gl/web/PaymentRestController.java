package com.sid.gl.web;

import com.sid.gl.dto.PaymentRequest;
import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.services.PaymentService;
import com.sid.gl.services.StudentService;
import com.sid.gl.utils.PaymentUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin("*")
public class PaymentRestController {
private PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;

    }

    @GetMapping(path = "/payments")
    public List<PaymentResponse> allPayments(){
        return paymentService.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<PaymentResponse> allPaymentsByCode(@PathVariable("code") String code){
        return paymentService.allPaymentsByCode(code);
    }

    @GetMapping(path = "/paymentsByStatus")
    public List<PaymentResponse> allPaymentsByStatus(@RequestParam PaymentStatus paymentStatus){
        return paymentService.allPaymentByStatus(paymentStatus);
    }


    @GetMapping(path = "/paymentsByType")
    public List<PaymentResponse> allPaymentsByType(@RequestParam PaymentType type){
        return paymentService.allPaymentsByType(type);
    }

    @GetMapping(path="/payments/{id}")
    public PaymentResponse getPaymentById(@PathVariable("id") Long id){
        return paymentService.getPaymentById(id);
    }

    @PutMapping(path = "payments/{id}")
    public PaymentResponse updatePaymentStatus(@RequestParam(value = "paymentStatus") PaymentStatus paymentStatus,@PathVariable("id") Long id){
        return paymentService.updatePaymentStatus(paymentStatus,id);
    }

    @PostMapping(path = "/payments",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PaymentResponse savePayment(@RequestParam(name = "file") MultipartFile file,
                              @RequestParam(name = "request") String paymentRequest) throws Exception {
        PaymentRequest request = PaymentUtils.convertJson(paymentRequest,PaymentRequest.class);
        return paymentService.savePayment(file,request);

    }

    @GetMapping(path = "/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
       return paymentService.getPaymentFile(paymentId);
    }

}
