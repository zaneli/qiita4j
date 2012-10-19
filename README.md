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
### 残りリクエスト可能数とRate Limit取得
`RateLimit rateLimit = client.getRateLimit();`  
### リクエストユーザーの情報取得
`UserInfo ownInfo = client.getOwnInfo();`  
### 特定ユーザーの情報取得
`UserInfo userInfo = client.getUserInfo(<ユーザー名>);`  
### 特定ユーザーの投稿取得
`PageableResponse<ItemInfo> userItemsPage = client.getUserItems(<ユーザー名>);`  
`ItemInfo[] userItems = userItemsPage.getContents();`  
### 特定ユーザーのストックした投稿取得
`PageableResponse<ItemInfo> userStockItemsPage = client.getUserStocks(<ユーザー名>);`  
`ItemInfo[] userStockItems = userStockItemsPage.getContents();`  
### 特定ユーザーのフォローしているユーザー取得
`PageableResponse<User> followingUsersPage = client.getFollowingUsers(<ユーザー名>);`  
`User[] followingUsers = followingUsersPage.getContents();`  
### 特定ユーザーのフォローしているタグ取得
`PageableResponse<TagInfo> followingTagsPage = client.getFollowingTags(<ユーザー名>);`  
`TagInfo[] followingTags = followingTagsPage.getContents();`  
### 特定タグの投稿取得
`PageableResponse<ItemInfo> tagItemsPage = client.getTagItems(<タグ名>);`  
`ItemInfo[] tagItems = tagItemsPage.getContents();`  
### タグ一覧取得
`PageableResponse<TagInfo> tagsPage = client.getTags();`  
`TagInfo[] tags = tagsPage.getContents();`  
### 検索結果取得
`PageableResponse<ItemInfo> searchResultItemsPage = client.searchItems(new SearchRequest(<検索文字列(複数指定可)>));`  
`ItemInfo[] searchResultItems = searchResultItemsPage.getContents();`  
### 新着投稿の取得
`PageableResponse<ItemInfo> newItemsPage = client.getNewItems();`  
`ItemInfo[] newItems = newItemsPage.getContents();`  
### 自分のストックした投稿の取得
`PageableResponse<ItemInfo> ownStockItemsPage = client.getOwnStocks();`  
`ItemInfo[] ownStockItems = ownStockItemsPage.getContents();`  
### 投稿の実行
`ItemRequest createItem = new ItemRequest();`  
`ItemInfo createResult = client.createItem(createItem);`  
### 投稿の更新
`ItemRequest updateItem = new ItemRequest();`  
`ItemInfo updateResult = client.updateItem(<UUID>, updateItem);`  
### 投稿の削除
`client.deleteItem(<UUID>);`  
### 特定の投稿取得
`ItemDetail detail = client.getSpecificItem(<UUID>);`  
### 投稿のストック
`client.stockItem(<UUID>);`  
### 投稿のストック解除
`client.unstockItem(<UUID>);`  
## ページネーション
### 取得件数の指定
`client.setPerPage(50);`  
### ページング
`PageableResponse<ItemInfo> userItemsPage = client.getUserItems(<ユーザー名>);`  

`PageableResponse<ItemInfo> firstPage = userItemsPage.getFirst();`  
`ItemInfo[] firstItems = firstPage.getContents();`  

`PageableResponse<ItemInfo> prevPage = userItemsPage.getPrev();`  
`ItemInfo[] prevItems = prevPage.getContents();`  

`PageableResponse<ItemInfo> nextPage = userItemsPage.getNext();`  
`ItemInfo[] nextItems = nextPage.getContents();`  

`PageableResponse<ItemInfo> lastPage = userItemsPage.getLast();`  
`ItemInfo[] lastItems = lastPage.getContents();`  