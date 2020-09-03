# 書籍管理システム

## 注意

OSのダークモードに仮対応していますが、やや見づらいのでライトモードでの実行を推奨します。

## 実行環境

実行には以下のアプリケーションのインストールが必要です。

* Webブラウザ (Chrome 85.0.4183.83以上推奨)
* Git
* OpenJDK 11以上
* Docker 19以上
    * Docker Compose 1.26以上

参考: 開発時の実行環境(Windows)

```
$ git --version
git version 2.28.0.windows.1
$ docker -v
Docker version 19.03.12, build 48a66213fe
$ docker-compose -v
docker-compose version 1.26.2, build eefe0d31
$ java -version
openjdk version "11.0.8" 2020-07-14
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.8+10)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.8+10, mixed mode)
```

参考: 開発時の実行環境(Mac)

```
$ git --version
git version 2.28.0
$ docker -v
Docker version 19.03.12, build 48a66213fe
$ docker-compose -v
docker-compose version 1.26.2, build eefe0d31
$ java -version
openjdk version "11.0.8" 2020-07-14
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.8+10)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.8+10, mixed mode)
```

## 実行手順

### ソースコードの入手

```
$ git clone https://github.com/de9/book-manager.git
$ cd book-manager
```

### JibによるバックエンドDockerイメージの作成

(Linux/Mac)

```
$ cd backend
$ ./gradlew clean jibDockerBuild
$ cd ..
```

(Windows)

```
$ cd backend
$ .\gradlew.bat clean jibDockerBuild
$ cd ..
```

※cleanを必ず実行して下さい

### Docker Composeの実行

```
$ docker-compose up -d --build
```

### アクセス

http://localhost:8080/

### 終了方法

```
$ docker-compose down
```

## テスト実行手順

(実行手順のソースコードの入手から)

### バックエンドテスト実行

(Linux/Mac)

```
$ cd backend
$ ./gradlew clean test
$ cd ..
```

(Windows)

```
$ cd backend
$ .\gradlew.bat clean test
$ cd ..
```

※cleanを必ず実行して下さい