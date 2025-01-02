<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=false displayInfo=false; section>

    <#-- Header Section -->
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Aydınlatma Metni Onayı</h1>
            <p>Giriş yapmak için aydınlatma metnini onaylayınız.</p>
        </div>
    <#elseif section == "form">

        <div id="kc-headerr">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Mobil İmza Aydınlatma Metni Onayı</h1>
            <p>Giriş yapmak için aydınlatma metnini okuyup onaylayınız.</p>
        </div>
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <p class="info-text">
                    Giriş işleminizi tamamlamak için aşağıdaki aydınlatma metnini dikkatlice okuyup onaylayınız.
                </p>

                <form id="kc-form-login" action="${actionUrl}" method="post">
                    <div class="consent-text">
                        <h2>Mobil İmza Aydınlatma Metni</h2>
                        <div class="consent-content">
                            <p>
                                İşbu aydınlatma metni, 6698 sayılı Kişisel Verilerin Korunması Kanunu ("KVKK") kapsamında kişisel verilerinizin
                                işlenme süreçleri hakkında sizleri bilgilendirmek amacıyla hazırlanmıştır. Keycloak sistemine girişiniz sırasında
                                verilen bilgiler, yalnızca güvenli kimlik doğrulama amacıyla kullanılacaktır ve üçüncü taraflarla paylaşılmayacaktır.
                            </p>
                            <p>
                                Detaylı bilgi için <a href="#">Gizlilik Politikası</a> bağlantısını ziyaret edebilirsiniz.
                            </p>
                        </div>
                    </div>

                    <div class="input-group consent-checkbox">
                        <label>
                            <input type="checkbox" name="consentApproval" value="true" required>
                            <span>Aydınlatma metnini okudum ve kabul ediyorum.</span>
                        </label>
                    </div>

                    <div id="kc-form-buttons">
                        <button type="button" class="btn-cancel" onclick="window.location.href='${url.loginAction}'">Geri Dön</button>
                        <input class="btn-submit" name="login" id="kc-login" type="submit" value="İmzala ve Devam Et" />
                    </div>
                </form>

            </div>
        </div>

        <#-- Footer -->
        <div id="kc-footer">
            <div id="footer-content">
                <span>© 2024 Mobil İmza Servisi. Tüm hakları saklıdır.</span>
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

        .consent-text h2 {
            text-align: center;
            margin-bottom: 15px;
            font-size: 18px;
            color: #333;
            font-weight: bold;
        }

        .consent-content {
            background-color: #f9f9f9;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            color: #555;
            line-height: 1.5;
            margin-bottom: 15px;
        }

        .consent-checkbox {
            text-align: center;
            margin-bottom: 15px;
        }

        .consent-checkbox label {
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 14px;
            color: #333;
        }

        .consent-checkbox input[type="checkbox"] {
            margin-right: 10px;
            cursor: pointer;
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

        #kc-attempted-username {
            display: none;
        }

        #reset-login{
            display: none;
        }

    </style>

</@layout.registrationLayout>
