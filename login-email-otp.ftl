<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('otp'); section>
    <#if section=="header">
        <div style="text-align: center; margin-bottom: 40px;">
            <h1 style="font-size: 28px; color: #333; font-weight: bold; margin: 0;">Projem Kapısı</h1>
            <h3 style="font-size: 20px; color: #666; font-weight: 500; margin-top: 10px;">EMAIL OTP DOĞRULAMA</h3>
        </div>
    <#elseif section=="form">
        <div style="display: flex; align-items: center; justify-content: center; height: 100vh; flex-direction: column; padding: 20px; background-color: #f9f9f9; opacity: 0; transform: scale(0.95); animation: fadeInUp 0.6s ease forwards;">
            <div style="text-align: center; margin-bottom: 20px;">
                <h1 style="font-size: 28px; color: #333; font-weight: bold;">Projem Kapısı</h1>
                <h3 style="font-size: 20px; color: #666; font-weight: 500;">EMAIL OTP DOĞRULAMA</h3>
            </div>
            <form id="kc-email-otp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post"
                  style="max-width: 380px; padding: 30px; border: 1px solid #ddd; border-radius: 12px; box-shadow: 0px 8px 24px rgba(0, 0, 0, 0.1); background-color: #ffffff; display: flex; flex-direction: column; align-items: center;">
                
                    <p style="color: #27ae60; font-size: 16px; font-weight: 500; margin-bottom: 16px;">Doğrulama yöntemi: <strong>Email OTP</strong></p>

                <div class="${properties.kcFormGroupClass!}" style="margin-bottom: 15px; width: 100%; display: flex; flex-direction: column; align-items: center;">
                    <div class="${properties.kcLabelWrapperClass!}" style="text-align: center; margin-bottom: 12px;">
                        <label for="otp" class="${properties.kcLabelClass!}" style="font-size: 16px; color: #555; font-weight: 600; margin-bottom:10px">
                            ${msg("loginOtpOneTime", "One-time code")}
                        </label>
                    </div>

                    <div class="${properties.kcInputWrapperClass!}" style="width: 100%; display: flex; justify-content: center;">
                        <input id="otp" name="otp" autocomplete="one-time-code" type="text" class="${properties.kcInputClass!}"
                               autofocus aria-invalid="<#if messagesPerField.existsError('otp')>true</#if>"
                               dir="ltr" style="width: 100%; max-width: 320px; padding: 14px; border: 1px solid #ccc; border-radius: 8px; font-size: 16px; box-shadow: inset 0px 4px 8px rgba(0, 0, 0, 0.05); transition: all 0.3s ease;" />

                    </div>
                </div>

                <div style="width: auto; margin-top: 20px;">
                    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                           name="login" id="kc-login" type="submit" value="${msg("doLogIn", "Sign In")}"
                           style="background-color: #007bff; color: white; border: none; padding: 10px 20px; font-size: 16px; font-weight: bold; border-radius: 8px; cursor: pointer; box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1); transition: all 0.3s ease; width: auto;"/>

                    <!-- Hover and Active Effects -->
                    <style>
                        #kc-login:hover {
                            background-color: #0056b3; /* Darker blue */
                            box-shadow: 0px 6px 16px rgba(0, 0, 0, 0.15);
                            transform: scale(1.02);
                        }

                        #kc-login:active {
                            background-color: #004085; /* Even darker blue */
                            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2);
                            transform: scale(1.01);
                        }

                        #try-another-way {
                            display: none;
                        }

                    </style>
                </div>
            </form>
        </div>
    </#if>
</@layout.registrationLayout>

<!-- Geçiş Efekti -->
<style>
    @keyframes fadeInUp {
        0% {
            opacity: 0;
            transform: scale(0.95);
        }
        100% {
            opacity: 1;
            transform: scale(1);
        }
    }
</style>
