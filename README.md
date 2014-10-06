# play_eccube_api
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/satoshi-m8a/play_eccube_api?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/satoshi-m8a/play_eccube_api.svg)](https://travis-ci.org/satoshi-m8a/play_eccube_api)

EC-CUBEのDBにアクセスできる、APIサーバーをPlayFramework2(Scala)を使って作っています。

EC-CUBEのDBを使いまわすことによって、既存のデータを無駄にすることなくAPIサーバーを構築することが
可能です。

JSONを返すAPIに特化することで既存のフレームワーク(AngularJS等)やスマホアプリとの連携が容易になります。
