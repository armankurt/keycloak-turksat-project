<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=messagesPerField.existsError('tcNumber') || messagesPerField.existsError('securityCode') displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>E-İmza Girişi</h1>
            <p>E-İmzanız ile Güvenli Giriş</p>
        </div>
    <#elseif section == "form">

        <div id="kc-headerr">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>E-İmza Girişi</h1>
            <p>E-İmzanız ile Güvenli Giriş</p>
        </div>
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <p class="info-text">
                    T.C. kimlik numaranız ve aşağıda gösterilen güvenlik kodunu girerek giriş yapabilirsiniz.
                </p>

                <form id="kc-form-login" action="${actionUrl}" method="post">
                    <div class="input-group">
                        <label for="tcNumber">T.C. Kimlik Numarası</label>
                        <input id="tcNumber" name="tcNumber" type="text" autocomplete="off" required />
                        <#if messagesPerField.existsError('tcNumber')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('tcNumber'))?no_esc}</span>
                        </#if>
                    </div>

                    <div class="input-group">
                        <label for="securityCode">Güvenlik Kodu</label>
                        <div class="captcha-container">
                            <div class="captcha">${securityCode}</div> <!-- Dinamik güvenlik kodu -->
                            <input id="securityCode" name="securityCode" type="text" autocomplete="off" required />
                        </div>
                        <#if messagesPerField.existsError('securityCode')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('securityCode'))?no_esc}</span>
                        </#if>
                    </div>

                    <div id="kc-form-buttons">
                        <button type="button" class="btn-cancel" onclick="window.location.href='${url.loginAction}'">Geri Dön</button>
                        <input class="btn-submit" name="login" id="kc-login" type="submit" value="Giriş Yap" />
                    </div>
                </form>

            </div>
        </div>

        <#-- Footer -->
        <div id="kc-footer">
            <div id="footer-content">
                <span>© 2024 E-İmza Servisi. Tüm hakları saklıdır.</span>
                <a href="#">Gizlilik Politikası</a> |
                <a href="#">Kullanım Şartları</a> |
                <a href="#">İletişim</a>
            </div>
        </div>
    </#if>

    <style>
        .info-text {
            text-align: center;
            margin-bottom: 20px;
            font-size: 14px;
            color: #555;
        }

        .input-group {
            margin-bottom: 15px;
        }

        .captcha-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .captcha {
            background-color: #f3f3f3;
            padding: 10px 20px;
            font-weight: bold;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .btn-cancel {
            background-color: #f44336;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            margin-right: 10px;
        }

        .btn-cancel:hover {
            background-color: #d32f2f;
        }

        .btn-submit {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }

        .btn-submit:hover {
            background-color: #388E3C;
        }

        #kc-footer {
            margin-top: 20px;
            text-align: center;
            font-size: 12px;
            color: #777;
        }

        #kc-footer a {
            color: #007BFF;
            text-decoration: none;
        }

        #kc-footer a:hover {
            text-decoration: underline;
        }

        .input-error {
            color: #f44336;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

        /* Logo ve Başlık */
        #kc-headerr {
            text-align: center;
            margin-bottom: 20px;
        }

        #kc-headerr .logo {
            width: 50px;
            margin-bottom: 10px;
        }

        #kc-headerr h1 {
            font-size: 22px;
            color: #333;
            margin: 0;
            font-weight: bold;
        }

        #kc-headerr p {
            font-size: 12px;
            color: #666;
            margin-top: 5px;
        }

        #try-another-way {
            margin-top: 10px;
            text-align: center;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>

</@layout.registrationLayout>
