/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mxp.user;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named("helloBean")
@RequestScoped
public class HelloBean {

    public void sayHi() {
        System.out.println("🎉 HelloBean.sayHi() was called!");
    }

    public String getGreeting() {
        return "Hello from HelloBean!";
    }
}
