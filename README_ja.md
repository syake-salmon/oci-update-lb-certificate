# oci-update-lb-certificate

![Build](https://github.com/syake-salmon/oci-update-lb-certificate/workflows/CI/badge.svg) | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate) | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=security_rating)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate) | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=coverage)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate)

*oci-update-lb-certificateはOCIロードバランサの証明書の自動更新をサポートするCUIアプリケーションです。*

for [English](README.md)
<hr />

## 前提
- OCI CLI: v2.12.11以上
- Java: 1.8.0_265以上

## 使い方
1. JARファイル(-with-dependenciesのほう)を任意の場所に配置する
2. `java -jar`コマンドを実行する

### コマンドライン引数
コマンドから、`-h`オプションを付与して実行することでも確認できます。

#### 必須
- --certificate-name <TEXT> : 追加する証明書バンドルの名称
- --listener-name <TEXT> : SSL証明書を更新する対象のリスナー名
- --load-balancer-id <TEXT> : 証明書バンドルを追加する対象のロードバランサのOCID

#### 任意
- --ca-certificate-file <FILENAME> : CA証明書のファイルパス
- --config-file <FILENAME> : OCI-CLIのコンフィグのファイルパス (デフォルト: ~/.oci/config)
- --max-wait-seconds <INTEGER> : OCIへの作業リクエストが完了するまでの最大待機時間（秒） (デフォルト: 1200)
- --passphrase <TEXT> : 秘密鍵ファイルのパスフレーズ
- --private-key-file <FILENAME> : PEM形式の秘密鍵のファイルパス
- --profile <TEXT> : 使用するOCI-CLIのプロファイル (デフォルト: DEFAULT)
- --public-certificate-file <FILENAME> : PEM形式のパブリック証明書のファイルパス
- --wait-interval-seconds <INTEGER>: OCIへの作業リクエストの完了をチェックする際のポーリング間隔（秒） (デフォルト: 30)
- -d or --debug : デバッグログ出力フラグ
- -h or --help  : ヘルプの出力フラグ
