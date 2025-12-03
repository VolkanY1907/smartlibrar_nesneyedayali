# SmartLibrary

SmartLibrary, Java, JDBC ve SQLite kullanılarak geliştirilmiş basit bir kütüphane yönetim sistemidir.

## Gereksinimler

Bu projeyi çalıştırmak için bilgisayarınızda aşağıdakilerin kurulu olması gerekir:

1.  **Java Development Kit (JDK) 11 veya üzeri**: Java kodlarını derlemek ve çalıştırmak için.
2.  **Apache Maven**: Proje bağımlılıklarını yönetmek ve projeyi derlemek için.

## Kurulum ve Çalıştırma

1.  Projeyi indirin veya klonlayın.
2.  Terminal veya komut satırını açın ve proje klasörüne gidin.
3.  Aşağıdaki komutu çalıştırarak projeyi derleyin ve başlatın:

```bash
mvn compile exec:java
```

## Visual Studio Code (VS Code) ile Çalıştırma

Eğer terminal komutları yerine **Visual Studio Code** kullanmak istiyorsanız:

1.  **VS Code'u Açın**: Bilgisayarınızda Visual Studio Code yüklü olmalıdır.
2.  **Klasörü Açın**: `File` > `Open Folder` (Dosya > Klasör Aç) diyerek bu projenin klasörünü seçin.
3.  **Eklentileri Yükleyin**: VS Code açıldığında sağ altta "Bu proje için önerilen eklentiler var" uyarısı çıkarsa "Install" (Yükle) butonuna basın. (Java Extension Pack gereklidir).
4.  **Projeyi Başlatın**:
    *   Sol taraftaki "Run and Debug" (Böcek simgesi) menüsüne gidin.
    *   Yukarıdaki açılır menüden **"SmartLibrary Başlat"** seçeneğini görün.
    *   Yeşil "Play" (Oynat) tuşuna basın.
    *   Program alt kısımdaki "Terminal" sekmesinde çalışacaktır.

## Kullanım

Program başladığında aşağıdaki menü seçeneklerini göreceksiniz:

1.  **Kitap Ekle**: Kütüphaneye yeni bir kitap ekler.
2.  **Kitapları Listele**: Kayıtlı tüm kitapları gösterir.
3.  **Öğrenci Ekle**: Sisteme yeni bir öğrenci kaydeder.
4.  **Öğrencileri Listele**: Kayıtlı öğrencileri gösterir.
5.  **Kitap Ödünç Ver**: Bir öğrenciye kitap ödünç verir (Eğer kitap zaten ödünçteyse uyarı verir).
6.  **Ödünç Listesini Görüntüle**: Şu anki ve geçmiş ödünç alma işlemlerini listeler.
7.  **Kitap Geri Teslim Al**: Ödünçteki bir kitabın iadesini alır.
0.  **Çıkış**: Programdan çıkar.

Veritabanı dosyası (`library.db`) proje dizininde otomatik olarak oluşturulacaktır.
