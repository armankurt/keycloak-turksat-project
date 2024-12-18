> **Keycloak Kurulumu ve Özelleştirme**
>
> **Talimatları**
>
> Bu belge, Docker ortamında Keycloak konteynırının kurulumu ve
> özelleştirilmesi için gerekli adımları içermektedir. Ayrıca,
> özelleştirilmiş jar dosyalarının nasıl yükleneceği ve işleneceği
> detaylı bir şekilde açıklanmaktadır.
>
> **Docker ile Keycloak Kurulumu**
>
> Docker üzerinde Keycloak konteynırını oluşturmak ve başlatmak için
> aşağıdaki komutlarıtakip ediniz:
>
> docker run -d \--name keycloak -p 8080:8080 -e KEYCLOAK_ADMIN=admin
> -e\
> KEYCLOAK_ADMIN_PASSWORD=admin123 quay.io/keycloak/keycloak:latest
> start-dev
>
> Bu işlem tamamlandığında Keycloak arayüzüne tarayıcınız üzerinden\
> http://localhost:8080 adresinden erişebilirsiniz. Yönetici hesabı
> olarak giriş yapmak için yukarıdaki komutta belirttiğiniz kullanıcı
> adı ve şifreyi kullanınız.
>
> **Keycloak Özelleştirilmiş Doğrulayıcıların**
>
> **Yüklenmesi ve Admin Konsoluna**
>
> **Eklenmesi**
>
> Bu belge, Maven kullanarak özelleştirilmiş doğrulayıcıların
> (authenticator) nasıl\
> oluşturulacağını, Docker üzerinden Keycloak konteynırına nasıl
> yükleneceğini ve bu doğrulayıcıların Keycloak Admin Konsolu üzerinden
> nasıl yapılandırılacağını açıklamaktadır.
>
> **1. Maven ile Özelleştirilmiş Doğrulayıcıların**
>
> **Oluşturulması**
>
> Her bir doğrulayıcı için aşağıdaki adımları takip ederek Maven ile jar
> dosyasını oluşturun ve Keycloak konteynırına yükleyin.
>
> **1.1 Doğrulayıcı: TcKimlikGiris**\
> Bu doğrulayıcı, kullanıcıların TC Kimlik Numarası ile giriş
> yapmalarını sağlar.
>
> cd
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/\
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/target/TcKiml
> ikGiris.jar keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **1.2 Doğrulayıcı: 2FaktörDoğrulamaMetotSecimi**\
> Bu doğrulayıcı, kullanıcının iki faktörlü doğrulama yöntemini
> seçmesini sağlar (örneğin, SMS, e-posta veya uygulama).
>
> cd\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDoğrulamaMetotS
> ecimi/\
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDoğrulamaMetotS
> ecimi/target/MetotSecimi.jar keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **1.3 Doğrulayıcı: 2FactorEmail**\
> Bu doğrulayıcı, e-posta üzerinden iki faktörlü doğrulama kodu
> göndermeyi sağlar.
>
> cd
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/target/2Fact
> orEmail.jar keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **1.4 Doğrulayıcı: MobileSign**\
> Bu doğrulayıcı, mobil imza üzerinden kimlik doğrulama yapılmasını
> sağlar.
>
> cd
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/\
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/target/MobileSi
> gn.jar keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **1.5 Doğrulayıcı: Esign**\
> Bu doğrulayıcı, e-imza kullanarak doğrulama işlemlerinin
> gerçekleştirilmesini sağlar.
>
> cd
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/\
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/target/Esign.jar
> keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **1.6 Doğrulayıcı: 2FactorSMS**\
> Bu doğrulayıcı, SMS yoluyla iki faktörlü doğrulama kodu göndermeyi
> sağlar.
>
> cd\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenti
> cator-main/\
> mvn clean install\
> docker cp\
> /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenti
> cator-main/target/2FactorSMS.jar keycloak:/opt/keycloak/providers/\
> docker restart keycloak
>
> **2. Admin Konsolunda Doğrulayıcıları Yapılandırma**
>
> Keycloak Admin Konsolu üzerinden yüklenen doğrulayıcıları
> yapılandırmak için aşağıdaki
>
> adımları takip edin.
>
> **Adım 1: Keycloak Admin Konsolu\'na Giriş**
>
> Tarayıcınız üzerinden Keycloak Admin Konsolu\'na erişin:
>
> http://localhost:8080
>
> Yönetici hesabı ile giriş yapın (**username:** admin, **password:**
> admin123).
>
> **Adım 2: Yeni Bir Authentication Flow Oluşturma**
>
> 1\. Sol menüden **Authentication** sekmesine gidin.
>
> 2\. **Flows** sekmesini açın.
>
> 3\. Sayfanın üst kısmındaki **Add flow** butonuna tıklayın.
>
> 4\. Yeni akışa bir isim verin (örneğin, MyBrowserFlow) ve **Save**
> butonuna tıklayarak
>
> kaydedin.
>
> **Adım 3: Adımları Ekleyerek Doğrulayıcıları Yapılandırma**
>
> Yeni oluşturulan akışa doğrulayıcıları eklemek için:
>
> 1\. **Add execution** butonuna tıklayarak adımları ekleyin.
>
> 2\. Yüklediğiniz doğrulayıcıları (örneğin, **TC Kimlik
> Authenticator**, **Email OTP**
>
> **Authenticator**, **SMS Authenticator**) sırayla seçin ve ekleyin.
>
> 3\. Eklediğiniz her adımın **Requirement** ayarını Required veya
> Alternative olarak
>
> yapılandırın.
>
> ○ **Required:** Adım zorunlu olarak çalışır.
>
> ○ **Alternative:** Kullanıcı alternatif doğrulama yöntemlerinden
> birini seçebilir.
>
> **Örnek Yapılandırma**
>
> Aşağıdaki gibi ağaç yapısında bir sıralama oluşturun:
>
> MyBrowserFlow
>
> │
>
> ├──Cookie (Alternative)
>
> ├──Identity Provider Redirector (Alternative)
>
> ├──Username or Email ile Giriş (Alternative)

+-----------------------------------+-----------------------------------+
| > │\                              | > ├──Username Password Form       |
| > │\                              | > (Required)\                     |
| > │\                              | > └──Method Selection             |
| > │\                              | > Authenticator (Required)\       |
| > │\                              | > ├──OTP Form for                 |
| > │\                              | > Username/Password Form          |
| > │\                              | > (Required) ├──Email             |
| > │\                              | > (Alternative)\                  |
| > │                               | > ├──Email OTP (Required)\        |
|                                   | > ├──SMS (Alternative)\           |
|                                   | > ├──SMS Authenticator            |
|                                   | > (Required)\                     |
|                                   | > └──TOTP (Disabled)\             |
|                                   | > ├──OTP Form (Required)          |
+===================================+===================================+
+-----------------------------------+-----------------------------------+

> │
>
> ├──TC Kimlik ile Giriş (Alternative)

+-----------------------------------+-----------------------------------+
| > │\                              | > ├──TC Kimlik Authenticator      |
| > │\                              | > (Required)\                     |
| > │\                              | > ├──Method Selection             |
| > │\                              | > Authenticator (Required)\       |
| > │\                              | > └──OTP Form TC Kimlik ile Giriş |
| > │\                              | > (Required)\                     |
| > │\                              | > ├──OTP Form for                 |
| > │\                              | > Username/Password Form          |
| > │\                              | > (Required) ├──Email             |
| > │                               | > (Alternative)\                  |
|                                   | > ├──Email OTP (Required)\        |
|                                   | > ├──SMS (Alternative)\           |
|                                   | > ├──SMS Authenticator            |
|                                   | > (Required)\                     |
|                                   | > └──TOTP (Disabled)\             |
|                                   | > ├──OTP Form (Required)          |
+===================================+===================================+
+-----------------------------------+-----------------------------------+

> │
>
> ├──Mobile Imza ile Giriş (Required)

+-----------------------------------+-----------------------------------+
| > │\                              | > ├──Mobile Sign                  |
| > │\                              | > Display(Required)\              |
| > │                               | > ├──Consent Form Authenticator   |
|                                   | > (Required)└──Waiting Form       |
|                                   | > Authenticator (Required)        |
+===================================+===================================+
+-----------------------------------+-----------------------------------+

> │\
> └──E-imza Giriş (Alternative)\
> ├──Esign Authenticator (Required)\
> └──E-sign Number Verification (Required)
>
> **Adım 4: Yeni Akışı Kullanıma Almak**\
> 1. **Bindings** sekmesine gidin.
>
> 2\. **Browser Flow** alanında, oluşturduğunuz yeni akışı (örneğin,
> MyBrowserFlow) seçin.
>
> 3\. **Save** butonuna tıklayarak değişiklikleri kaydedin.
>
> **Keycloak Tema Oluşturma**
>
> Bu rehber, Keycloak üzerinde özel bir tema oluşturmak, dosya yapısını
> düzenlemek ve temayı Keycloak Admin Konsolu üzerinden aktif hale
> getirmek için gerekli adımları detaylı birşekilde açıklamaktadır.
>
> **1. Keycloak Konteynırına Erişim**\
> Keycloak konteynırının içine erişim sağlamak için aşağıdaki komutu
> kullanın:\
> docker exec -it keycloak /bin/bash\
> Bu komut, Keycloak konteynırının terminaline bağlanmanızı sağlar.
> Buradan dosya yapısınıinceleyebilir ve özelleştirilmiş tema
> klasörlerini oluşturabilirsiniz.
>
> **Özel Tema Klasörünün Oluşturulması**\
> Keycloak temaları, /opt/keycloak/themes/ dizini altında yer alır.
> Buraya yeni bir tema klasörü oluşturmanız gerekmektedir.
>
> **Tema Yapısını Oluşturma**

+-----------------------------------+-----------------------------------+
| 1\.                               | > **Temalar dizinine gidin:**     |
+===================================+===================================+
+-----------------------------------+-----------------------------------+

> cd /opt/keycloak/themes/\
> 2. **Yeni tema klasörünü oluşturun:**\
> mkdir my-new-theme
>
> **Tema alt klasörlerini oluşturun:** Tema için gerekli klasör yapısını
> aşağıdaki komutlarla oluşturabilirsiniz:\
> mkdir -p my-new-theme/login/resources/img

+-----------------------+-----------------------+-----------------------+
| 3\.                   | > mkdir -p            |                       |
|                       | > my-new-theme/common |                       |
+=======================+=======================+=======================+
|                       | ○                     | > login: Giriş        |
|                       |                       | > sayfası için FTL    |
|                       |                       | > dosyalarını         |
|                       |                       | > içerecek klasör.    |
+-----------------------+-----------------------+-----------------------+
|                       | ○                     | > resources/img:      |
|                       |                       | > Temada kullanılacak |
|                       |                       | > resimler için       |
|                       |                       | > klasör.             |
+-----------------------+-----------------------+-----------------------+
|                       | ○                     | > common: Tema için   |
|                       |                       | > ortak CSS ve JS     |
|                       |                       | > dosyalarının        |
|                       |                       | > yükleneceği klasör. |
+-----------------------+-----------------------+-----------------------+

> **Klasör Yapısı**
>
> Oluşturduğunuz klasör yapısı şu şekilde görünmelidir:
>
> /opt/keycloak/themes/my-new-theme
>
> │
>
> ├──login
>
> │ ├──resources
>
> │ └──img
>
> ├──common
>
> **3. FTL ve Kaynak Dosyalarının Yüklenmesi**
>
> Özelleştirilmiş tema dosyalarınızı (FTL, CSS, resimler vb.) ilgili
> klasörlere yüklemeniz gerekmektedir. Bunun için konteynır dışında
> hazırladığınız dosyaları aşağıdaki komutlarla kopyalayabilirsiniz:
>
> **FTL Dosyalarının Yüklenmesi**
>
> docker cp
> /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login.ftl
> keycloak:/opt/keycloak/themes/my-new-theme/login/
>
> docker cp
> /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-reset-password.ftl
> keycloak:/opt/keycloak/themes/my-new-theme/login/
>
> **Resim Dosyalarının Yüklenmesi**
>
> docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/img.png
>
> keycloak:/opt/keycloak/themes/my-new-theme/login/resources/img/
>
> docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/tt.png
>
> keycloak:/opt/keycloak/themes/my-new-theme/login/resources/img/
>
> **4. Temayı Aktif Hale Getirme**
>
> Özel temayı aktif hale getirmek için Keycloak Admin Konsolu
> kullanılır.
>
> **Adımlar:**
>
> 1\. **Keycloak Admin Konsolu\'na erişim sağlayın:** Tarayıcınızdan
>
> http://localhost:8080 adresine giderek yönetici hesabı ile giriş
> yapın.
>
> 2\. **Realm Ayarlarına Gidin:**
>
> ○ Sol menüden **Realm Settings** (Realm Ayarları) seçeneğine tıklayın.
>
> 3\. **Temayı Seçin:**
>
> ○ **Themes** sekmesine geçin.
>
> ○ **Login Theme** (Giriş Teması) alanında, oluşturduğunuz temayı
>
> (my-new-theme) seçin.

+-----------------------+-----------------------+-----------------------+
| 4\.                   | > **Değişiklikleri    |                       |
|                       | > Kaydedin:**         |                       |
+=======================+=======================+=======================+
|                       | ○                     | > Sayfanın altında    |
|                       |                       | > bulunan **Save**    |
|                       |                       | > butonuna tıklayarak |
|                       |                       | > değişiklikleri      |
|                       |                       | > kaydedin.           |
+-----------------------+-----------------------+-----------------------+

> **5. Keycloak Konteynırını Yeniden Başlatma**
>
> Tema yüklemelerini tamamladıktan sonra Keycloak konteynırını yeniden
> başlatmanız
>
> gerekmektedir:
>
> docker restart keycloak
>
> Bu işlem, yeni temanın Keycloak tarafından tanınmasını sağlar.
>
> **Logların İncelenmesi**
>
> Konteynırın loglarını izlemek ve olası hataları tespit etmek için şu
> komutu kullanabilirsiniz:
>
> docker logs -f keycloak
>
> **Önemli Not**\
> Bu belgedeki komutlarda yer alan dosya yolları\
> (/Users/armanyilmazkurt/Desktop/\...) örnek olarak verilmiştir. Kendi
> sisteminizde bu yolları dosyalarınızın bulunduğu dizinlere göre
> düzenlemeyi unutmayınız. Özellikle farklıişletim sistemleri
> kullanıyorsanız (örneğin Windows), dosya yollarını buna uygun biçimde
> değiştirmelisiniz.
