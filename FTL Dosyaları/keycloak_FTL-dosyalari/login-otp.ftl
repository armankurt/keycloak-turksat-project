<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('totp'); section>
    <#if section=="header">
        <div style="text-align: center; margin-bottom: 40px;">
            <h1 style="font-size: 30px; color: #333; font-weight: bold; margin: 0;">Projem Kapısı</h1>
            <h3 style="font-size: 18px; color: #666; font-weight: 500; margin-top: 10px;">KİMLİK DOĞRULAMA SİSTEMİ</h3>
        </div>
    <#elseif section=="form">
        <div style="display: flex; align-items: center; justify-content: center; height: 100vh; flex-direction: column;">
            <div style="text-align: center; margin-bottom: 20px;">
                <h1 style="font-size: 28px; color: #333; font-weight: bold;">Projem Kapısı</h1>
                <h3 style="font-size: 20px; color: #666; font-weight: 500;">KİMLİK DOĞRULAMA SİSTEMİ</h3>
            </div>
            <form id="kc-otp-login-form" action="${url.loginAction}" method="post"
                  style="max-width: 450px; padding: 40px; border: none; border-radius: 12px; box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.15); background-color: #ffffff; display: flex; flex-direction: column; align-items: center;">

                <#-- TOTP doğrulama formunu göster -->
                <#if authenticationSession??>
                    <p style="color: #4CAF50; font-weight: bold; margin-bottom: 20px; font-size: 18px;">Doğrulama Yöntemi: TOTP</p>
                <#else>
                    <p style="color: red; text-align: center;">Authentication session mevcut değil.</p>
                </#if>

                <div style="width: 100%; margin-bottom: 10px; display: flex; flex-direction: column; align-items: center;">
                    <label for="otp" style="font-size: 16px; color: #555; font-weight: 600; display: block; text-align: center; margin-bottom: 8px;">
                        ${msg("loginOtpOneTime")}
                    </label>
                    <input id="otp" name="otp" autocomplete="one-time-code" type="text"
                           style="width: 100%; max-width: 300px; padding: 12px; border: 1px solid #ddd; border-radius: 6px; font-size: 16px; box-shadow: inset 0px 2px 4px rgba(0, 0, 0, 0.05); text-align: left; margin-bottom: 10px; display: block;">
                </div>

                <#if messagesPerField.existsError('totp')>
                    <span style="color: #e74c3c; font-size: 14px; text-align: center; display: block;">
                        ${kcSanitize(messagesPerField.get('totp'))?no_esc}
                    </span>
                </#if>

                <button type="submit" style="background-color: #007bff; color: white; padding: 12px 24px; font-size: 16px; font-weight: bold; border: none; border-radius: 6px; cursor: pointer; transition: background-color 0.3s, transform 0.2s; width: 100%; max-width: 200px; margin-top: 20px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
                    ${msg("doLogIn")}
                </button>

                <!-- Hover and Active Effects -->
                <style>
                    button:hover {
                        background-color: #0056b3; /* Darker blue */
                        transform: scale(1.05); /* Slightly enlarge */
                        box-shadow: 0px 6px 16px rgba(0, 0, 0, 0.2);
                    }

                    button:active {
                        background-color: #004085; /* Even darker blue */
                        box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.3);
                        transform: scale(1.02);
                    }
                    
                    #try-another-way {
                        display: none;
                    }
                </style>
            </form>
        </div>
    </#if>
</@layout.registrationLayout>
