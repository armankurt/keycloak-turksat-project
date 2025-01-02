<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=messagesPerField.existsError('tcNumber') || messagesPerField.existsError('password') displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Projem Kapısı</h1> <!-- Static Title -->
            <p>T.C. KİMLİK DOĞRULAMA</p>
        </div>
    <#elseif section == "form">
        <div id="kc-headerr">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>T.C. KİMLİK DOĞRULAMA</h1>
            <p>T.C. KİMLİK DOĞRULAMA ile Güvenli Giriş</p>
        </div>
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <p class="info-text">
                    T.C. Kimlik Kartınızı ve şifrenizi kullanarak kimliğinizi doğruladıktan sonra işleminize kaldığınız yerden devam edebilirsiniz.
                </p>

                <form id="kc-form-login" action="${actionUrl}" method="post">
                    <div class="input-group">
                        <label for="tcNumber">T.C. Kimlik No</label>
                        <input id="tcNumber" name="tcNumber" type="text" autocomplete="off" required />
                        <#if messagesPerField.existsError('tcNumber')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('tcNumber'))?no_esc}</span>
                        </#if>
                    </div>

                    <div class="input-group">
                        <label for="password">Şifre</label>
                        <input id="password" name="password" type="password" autocomplete="off" required />
                        <#if messagesPerField.existsError('password')>
                            <span class="input-error">${kcSanitize(messagesPerField.getFirstError('password'))?no_esc}</span>
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
                <span>© 2024 Projem Kapısı. Tüm hakları saklıdır.</span>
                <a href="#">Gizlilik Politikası</a> |
                <a href="#">Kullanım Şartları</a> |
                <a href="#">İletişim</a>
            </div>
        </div>
    </#if>

    <style>
            #try-another-way {
                margin-top: 10px;
                text-align: center;
                color: #333;
                display: flex;
                justify-content: center;
                align-items: center;
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
    </style>

</@layout.registrationLayout>


