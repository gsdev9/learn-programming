# learn-programming
Graduation Project

## 開発環境のセットアップ手順
1. Java8 インストール

  ```
  brew cask install java8
  export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
  ```
2. MySQL, sbt インストール

  ```
  brew install mysql
  brew install sbt
  ```
3. 環境変数設定

  ```
  cat <<EOL >> ~/.bash_profile
  echo export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
  echo export PATH=$JAVA_HOME/bin:/usr/local/bin:$PATH
  EOL
  source ~/.bash_profile
  ```
  
## アプリのインストール・起動
1. リポジトリのクローン
```
git clone https://github.com/gsdev9/learn-programming.git
```
2. フォルダに移動
```
cd learn-programming
```
3. sbtの起動
```
sbt run
```

## MySQLの設定
1. DBスキーマの作成
```
mysql -uroot -e ‘create database learn-programming charset=utf8mb4’
```
2. Mysqlの起動
```
sudo mysql.server start
```

## intelliJへプロジェクトのインポート
1. import project を選択し、githubからクローンしたリポジトリを選択
2. sbtプロジェクトを選択
3. あとはデフォルト設定でOK