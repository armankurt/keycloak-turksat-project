<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>

<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username', 'password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>

    <#-- Header Section -->
    <#if section == "header">
    <div id="kc-headerr">
        <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
        <h1>Projem Kapısı</h1> <!-- Static Title -->
        <p>KİMLİK DOĞRULAMA SİSTEMİ</p>
    </div>

    <#-- Form Section -->
    <#elseif section == "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">

                <p class="info-text">
                    Kullanıcı Adı ve Projem Şifrenizi kullanarak kimliğinizi doğruladıktan sonra işleminize kaldığınız yerden devam edebilirsiniz.
                </p>

                <#-- Login Form -->
                <form id="kc-form-login" action="${url.loginAction}" method="post">
                    <input type="hidden" id="login-method" name="login-method" value="username">

                    <div class="login-section">
                        <div class="input-group">
                            <label for="username">Kullanıcı Adı veya Email</label>
                            <input tabindex="2" id="username" class="${properties.kcInputClass!}" name="username" type="text" autocomplete="username"
                                   aria-invalid="<#if messagesPerField.existsError('username', 'password')>true</#if>" />
                            <#if messagesPerField.existsError('username', 'password')>
                                <span class="input-error" aria-live="polite">
                                    ${kcSanitize(messagesPerField.getFirstError('username', 'password'))?no_esc}
                                </span>
                            </#if>
                        </div>

                        <div class="input-group">
                            <label for="password">Projem Şifresi</label>
                            <input tabindex="3" id="password" class="${properties.kcInputClass!}" name="password" type="password" autocomplete="current-password"
                                   aria-invalid="<#if messagesPerField.existsError('username', 'password')>true</#if>" />
                            <button type="button" class="input-icon" onclick="togglePasswordVisibility('password')">
                                <i class="fa fa-eye" aria-hidden="true"></i>
                            </button>
                            <#if messagesPerField.existsError('username', 'password')>
                                <span class="input-error" aria-live="polite">
                                    ${kcSanitize(messagesPerField.getFirstError('username', 'password'))?no_esc}
                                </span>
                            </#if>
                        </div>
                    </div>

                    <p class="forgot-password-text">
                        * <a href="${url.loginResetCredentialsUrl}">Şifremi Unuttum</a>
                    </p>

                    <#-- Login and Cancel Buttons -->
                    <div id="kc-form-buttons">
                        <button type="button" class="btn-cancel">İptal</button>
                        <input tabindex="7" class="btn-submit" name="login" id="kc-login" type="submit" value="Giriş Yap" />
                    </div>
                </form>
            </div>
        </div>

        <#-- Additional Login Options
        <div id="login-options-wrapper">
            <p class="options-title">Veya Farklı Bir Yöntemle Giriş Yapın</p>
            <div id="kc-extra-buttons">
                <button class="btn-extra" 
                    onclick="window.location.href='${url.loginAction}?execution=tc-kimlik-giris'">
                    T.C. Kimlik ile Giriş Yap
                </button>

                <button class="btn-extra" onclick="redirectToMobileSignatureLogin()">
                    Mobil İmza ile Giriş Yap
                </button>
            </div>
        </div>
        -->

        <#-- Footer -->
        <div id="kc-footer">
            <div id="footer-content">
                <span>© 2024 Projem Kapısı. Tüm hakları saklıdır.</span>
                <a href="#">Gizlilik Politikası</a> |
                <a href="#">Kullanım Şartları</a> |
                <a href="#">İletişim</a>
            </div>
        </div>

        <#-- JavaScript -->
        <script type="text/javascript">
            function togglePasswordVisibility(passwordId) {
                const passwordInput = document.getElementById(passwordId);
                const icon = passwordInput.nextElementSibling.querySelector("i");
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                } else {
                    passwordInput.type = "password";
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }

        </script>

        <#-- Custom Styles -->
        <style>

            #kc-form {
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                margin: 0 auto;
                width: 100%;
            }

            .input-group {
                display: flex;
                flex-direction: column;
                margin-bottom: 20px;
                width: 100%;
            }

            .input-group label {
                margin-bottom: 5px;
                font-size: 14px;
            }

            .input-group input {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                width: 100%;
            }
            #login-options-wrapper {
                margin-top: 40px;
                text-align: center;
            }

            .options-title {
                font-size: 16px;
                margin-bottom: 15px;
                color: #333;
            }

            #kc-extra-buttons {
                display: flex;
                justify-content: center;
                gap: 20px;
            }

            .btn-extra {
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 6px;
                padding: 12px 25px;
                font-size: 14px;
                font-weight: 500;
                cursor: pointer;
                transition: all 0.3s ease;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .btn-extra:hover {
                background-color: #0056b3;
                transform: scale(1.05);
            }

            .btn-extra:active {
                background-color: #003f8a;
                transform: scale(1);
            }

            #try-another-way {
                margin-top: 10px;
                text-align: center;
                color: #333;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            #kc-attempted-username {
                display: none;
            }

            #reset-login{
                display: none;
            }
        </style>

    </#if>

</@layout.registrationLayout>
