#Qiita4j  
[Qiita API](http://qiita.com/docs "Qiita API document") の Java ラッパーライブラリです。  
## トークンの取得
`QiitaClient client = new QiitaClient();`  
`TokenInfo tokenInfo = client.authorize(new AuthRequest(<ユーザー名>, <パスワード>));`  
`System.out.println(tokenInfo.getToken());`  
## トークンの設定
`QiitaClient client = new QiitaClient(<トークン>);`  
または  
`QiitaClient client = new QiitaClient();`  
`client.setToken(<トークン>);`  
## Qiita API の実行
QiitaClient のメソッドを使用して実行します。
