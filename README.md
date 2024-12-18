# Keycloak Kurulumu ve Özelleştirme Talimatları

Keycloak, modern kimlik doğrulama ve yetkilendirme işlemleri için kullanılan, açık kaynaklı bir kimlik sağlayıcıdır. Bu belge, Docker ortamında Keycloak konteynırının kurulumu ve özelleştirilmesi için gereken tüm adımları detaylı bir şekilde açıklamaktadır. Ayrıca, özelleştirilmiş doğrulayıcıların (authenticator) oluşturulması, bu doğrulayıcıların Keycloak üzerine yüklenmesi ve doğru bir şekilde yapılandırılması konularında rehberlik etmektedir.

Bu belge, özellikle kullanıcıların kimlik doğrulama sürecini kişiselleştirmek ve sistem gereksinimlerine uygun hale getirmek isteyen yazılım geliştiriciler için hazırlanmıştır. Örneğin, bir projede kullanıcıların TC Kimlik Numarası ile giriş yapabilmesi, e-posta veya SMS yoluyla iki faktörlü doğrulama gerçekleştirebilmesi ya da mobil imza ve e-imza gibi ileri düzey güvenlik yöntemlerini kullanabilmesi gerekebilir. Burada ele alınan yöntemler, bu gibi ihtiyaçlara yanıt verecek şekilde tasarlanmıştır.

Belgede ayrıca Keycloak temasının özelleştirilmesine ilişkin bilgiler de bulunmaktadır. Özel bir giriş sayfası tasarlamak, şirket markasına uygun bir görünüm ve his yaratmak isteyen projeler için özel tema oluşturma ve düzenleme adımları detaylandırılmıştır. Keycloak’ı kullanarak kimlik doğrulama süreçlerinizi hem güvenli hem de kullanıcı dostu bir hale getirmek için ihtiyacınız olan tüm teknik bilgiler bu belgede yer almaktadır.

Bu proje, kurumsal düzeyde bir uygulamanın kimlik doğrulama altyapısını oluşturmayı hedeflemektedir. Kullanıcıların farklı yöntemlerle sisteme güvenli bir şekilde erişmesini sağlamak için birden fazla özelleştirilmiş doğrulama seçeneği sunulmuştur. Ayrıca, giriş süreçlerinin görsel ve işlevsel olarak kuruma özel bir şekilde özelleştirilmesi amaçlanmaktadır. Bu belgede yer alan rehber, projeyi başarılı bir şekilde hayata geçirmek isteyen geliştiriciler için adım adım yol gösterici bir kaynak niteliğindedir.

---

## Docker ile Keycloak Kurulumu

Docker üzerinde Keycloak konteynırını oluşturmak ve başlatmak için aşağıdaki komutları takip ediniz:

```bash
docker run -d --name keycloak -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin123 quay.io/keycloak/keycloak:latest start-dev
```

Bu işlem tamamlandığında Keycloak arayüzüne tarayıcınız üzerinden [http://localhost:8080](http://localhost:8080) adresinden erişebilirsiniz. Yönetici hesabı olarak giriş yapmak için yukarıdaki komutta belirttiğiniz kullanıcı adı ve şifreyi kullanınız.

---

## Keycloak Özelleştirilmiş Doğrulayıcıların Yüklenmesi ve Admin Konsoluna Eklenmesi

Bu bölümde Maven kullanarak özelleştirilmiş doğrulayıcıların (authenticator) nasıl oluşturulacağı, Docker üzerinden Keycloak konteynırına nasıl yükleneceği ve bu doğrulayıcıların Keycloak Admin Konsolu üzerinden nasıl yapılandırılacağı anlatılmaktadır.

### 1. Maven ile Özelleştirilmiş Doğrulayıcıların Oluşturulması
Her bir doğrulayıcı için aşağıdaki adımları takip ederek Maven ile jar dosyasını oluşturun ve Keycloak konteynırına yükleyin.

#### 1.1 Doğrulayıcı: TcKimlikGiris
Bu doğrulayıcı, kullanıcıların TC Kimlik Numarası ile giriş yapmalarını sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator1-TcKimlikGiris/target/TcKimlikGiris.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.2 Doğrulayıcı: 2FaktörDoğrulamaMetotSecimi
Bu doğrulayıcı, kullanıcının iki faktörlü doğrulama yöntemini seçmesini sağlar (SMS, e-posta veya uygulama).

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDog̈rulamaMetotSecimi/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator2-2FaktörDog̈rulamaMetotSecimi/target/MetotSecimi.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.3 Doğrulayıcı: 2FactorEmail
Bu doğrulayıcı, e-posta üzerinden iki faktörlü doğrulama kodu göndermeyi sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator3-2FactorEmail/target/2FactorEmail.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.4 Doğrulayıcı: MobileSign
Bu doğrulayıcı, mobil imza üzerinden kimlik doğrulama yapılmasını sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator4-MobileSign/target/MobileSign.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.5 Doğrulayıcı: Esign
Bu doğrulayıcı, e-imza kullanarak doğrulama işlemlerinin gerçekleştirilmesini sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator5-Esign/target/Esign.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

#### 1.6 Doğrulayıcı: 2FactorSMS
Bu doğrulayıcı, SMS yoluyla iki faktörlü doğrulama kodu göndermeyi sağlar.

```bash
cd /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenticator-main/
mvn clean install
docker cp /Users/armanyilmazkurt/Desktop/keycloak/custom-authenticator6-keycloak-2fa-sms-authenticator-main/target/2FactorSMS.jar keycloak:/opt/keycloak/providers/
docker restart keycloak
```

# Keycloak Admin Konsolunda Doğrulayıcıları Yapılandırma

Keycloak Admin Konsolu üzerinden yüklenmiş doğrulayıcıların yapılandırılması için aşağıdaki adımları takip ediniz.

---

## Adım 1: Keycloak Admin Konsolu'na Giriş

1. Tarayıcınız üzerinden [http://localhost:8080](http://localhost:8080) adresine gidin.
2. Yönetici hesabı (username: **admin**, password: **admin123**) ile giriş yapın.

---

## Adım 2: Yeni Bir Authentication Flow Oluşturma

1. Sol menüden **Authentication** sekmesine gidin.
2. **Flows** sekmesini açın.
3. Sayfanın üst kısmındaki **Add flow** butonuna tıklayın.
4. Yeni akışa bir isim verin (mesela **MyBrowserFlow**) ve **Save** butonuna tıklayarak kaydedin.

---

## Adım 3: Adımları Ekleyerek Doğrulayıcıları Yapılandırma

Yeni oluşturulan akışa doğrulayıcıları eklemek için:

1. **Add execution** butonuna tıklayarak adımları ekleyin.
2. Yüklediğiniz doğrulayıcıları (mesela, **TC Kimlik Authenticator**, **Email OTP Authenticator**, **SMS Authenticator**) sırayla seçin ve ekleyin.
3. Eklediğiniz her adımın **Requirement** ayarını yapılandırın:
   - **Required**: Adım zorunlu olarak çalışır.
   - **Alternative**: Kullanıcı alternatif doğrulama yöntemlerinden birini seçebilir.

### Örnek Yapılandırma

Aşağıdaki gibi ağaç yapısında bir sıralama oluşturun:

```
MyBrowserFlow
│
├── Cookie (Alternative)
├── Identity Provider Redirector (Alternative)
├── Username or Email ile Giriş (Alternative)
│   ├── Username Password Form (Required)
│   └── Method Selection Authenticator (Required)
│       ├── OTP Form for Username/Password Form (Required)
│       ├── Email (Alternative)
│		├── Email OTP (Required)
│       ├── SMS (Alternative)
│		├── SMS Authenticator (Required)
│       └── TOTP (Disabled)
│		├── OTP Form (Required)
│
├── TC Kimlik ile Giriş (Alternative)
│   ├── TC Kimlik Authenticator (Required)
│   ├── Method Selection Authenticator (Required)
│   └── OTP Form TC Kimlik ile Giriş (Required)
│       ├── OTP Form for Username/Password Form (Required)
│       ├── Email (Alternative)
│		├── Email OTP (Required)
│       ├── SMS (Alternative)
│		├── SMS Authenticator (Required)
│       └── TOTP (Disabled)
│		├── OTP Form (Required)
│
├── Mobile Imza ile Giriş (Required)
│   ├── Mobile Sign Display(Required)
│   ├── Consent Form Authenticator (Required)
│   └── Waiting Form Authenticator (Required)
│
└── E-imza Giriş (Alternative)
    ├── Esign Authenticator (Required)
    └── E-sign Number Verification (Required)
```

---

## Adım 4: Yeni Akışı Kullanıma Almak

1. **Bindings** sekmesine gidin.
2. **Browser Flow** alanında, oluşturduğunuz yeni akışı (**MyBrowserFlow**) seçin.
3. **Save** butonuna tıklayarak değişiklikleri kaydedin.

# Keycloak Tema Oluşturma Rehberi

Bu rehber, Keycloak üzerinde özel bir tema oluşturmak, dosya yapısını düzenlemek ve temayı Keycloak Admin Konsolu üzerinden aktif hale getirmek için gerekli adımları detaylı bir şekilde açıklamaktadır.

---

## 1. Keycloak Konteynırına Erişim

Keycloak konteynırının içine erişim sağlamak için aşağıdaki komutu kullanın:

```bash
docker exec -it keycloak /bin/bash
```

Bu komut, Keycloak konteynırının terminaline bağlanmanızı sağlar. Buradan dosya yapısını inceleyebilir ve özelleştirilmiş tema klasörlerini oluşturabilirsiniz.

---

## 2. Özel Tema Klasörünün Oluşturulması

Keycloak temaları, `/opt/keycloak/themes/` dizini altında yer alır. Buraya yeni bir tema klasörü oluşturmanız gerekmektedir.

### Tema Yapısını Oluşturma

1. **Temalar** dizinine gidin:
   ```bash
   cd /opt/keycloak/themes/
   ```

2. **Yeni tema klasörünü oluşturun:**
   ```bash
   mkdir my-new-theme
   ```

3. **Tema alt klasörlerini oluşturun:** Tema için gerekli klasör yapısını aşağıdaki komutlarla oluşturabilirsiniz:
   ```bash
   mkdir -p my-new-theme/login/resources/img
   mkdir -p my-new-theme/common
   ```

### Tema Klasörü Yapısı

Oluşturduğunuz klasör yapısı şu şekilde görünmelidir:

```
/opt/keycloak/themes/my-new-theme
│
├── login
│   ├── resources
│       └── img
├── common
```

- **login**: Giriş sayfası için FTL dosyalarını içerecek klasör.
- **resources/img**: Temada kullanılacak resimler için klasör.
- **common**: Tema için ortak CSS ve JS dosyalarının yükleneceği klasör.

---

## 3. FTL ve Kaynak Dosyalarının Yüklenmesi

Özelleştirilmiş tema dosyalarınızı (FTL, CSS, resimler vb.) ilgili klasörlere yüklemeniz gerekmektedir. Bunun için konteynır dışında hazırladığınız dosyaları aşağıdaki komutlarla kopyalayabilirsiniz:

### FTL Dosyalarının Yüklenmesi

```bash
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/consent-form.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-config-totp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-email-otp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-esign-number.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-esign.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-method-selection.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-otp.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-reset-password.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-sms.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login-tc.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/login.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/mobilsign-authentication.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/select-authenticator.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
docker cp /Users/armanyilmazkurt/Desktop/keycloak_FTL-dosyalari/waiting-form.ftl keycloak:/opt/keycloak/themes/my-new-theme/login/
```

### Resim Dosyalarının Yüklenmesi

```bash
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/img.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/tt.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/turkcell.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/vodafone.png /opt/keycloak/themes/my-new-theme/login/resources/img
docker cp /Users/armanyilmazkurt/Desktop/keycloak_Resimleri/wait.png /opt/keycloak/themes/my-new-theme/login/resources/img
```

---

## 4. Temayı Aktif Hale Getirme

Özel temayı aktif hale getirmek için Keycloak Admin Konsolu kullanılır.

### Adımlar:

1. **Keycloak Admin Konsolu'na erişim sağlayın:**
   Tarayıcınızdan [http://localhost:8080](http://localhost:8080) adresine giderek yönetici hesabı ile giriş yapın.

2. **Realm Ayarlarına gidin:**
   Sol menüden **Realm Settings** (Realm Ayarları) seçeneğine tıklayın.

3. **Temayı seçin:**
   **Themes** sekmesine geçin. **Login Theme** (Giriş Teması) alanında, oluşturduğunuz temayı (**my-new-theme**) seçin.

4. **Değişiklikleri kaydedin:**
   Sayfanın altında bulunan **Save** butonuna tıklayarak değişiklikleri kaydedin.

---

## 5. Keycloak Konteynırını Yeniden Başlatma

Tema yüklemelerini tamamladıktan sonra Keycloak konteynırını yeniden başlatmanız gerekmektedir:

```bash
docker restart keycloak
```

Bu işlem, yeni temanın Keycloak tarafından tanınmasını sağlar.

---

## Logların İncelenmesi

Konteynırın loglarını izlemek ve olası hataları tespit etmek için şu komutu kullanabilirsiniz:

```bash
docker logs -f keycloak
```

---

## Önemli Not

Bu belgedeki komutlarda yer alan dosya yolları (`/Users/armanyilmazkurt/Desktop/...`) örnek olarak verilmiştir. Kendi sisteminizde bu yolları dosyalarınızın bulunduğu dizinlere göre düzenlemeyi unutmayınız. Özellikle farklı işletim sistemleri kullanıyorsanız (Windows gibi), dosya yollarını buna uygun biçimde değiştirmelisiniz.



