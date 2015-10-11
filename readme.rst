AsakusaFW Hishidama Test tool
=============================
`Asakusa Framework <http://www.ne.jp/asahi/hishidama/home/tech/asakusafw/index.html>`_ で作ったアプリケーションのテストに使うツールです。

例えば、MasterJoinやGroupSort等の演算子メソッドに対し、グルーピングキーに従って入力データをソートしてメソッドを呼び出すことが出来ます。


インストール方法
----------------
Asakusaプロジェクトのbuild.gradleに以下のようなリポジトリーおよび依存関係を追加して、Eclipseプロジェクト情報を再作成して下さい。

.. sourcecode:: gradle

 repositories {
     maven { url 'http://hishidama.github.io/asakusafw-tester/' }
 }
 
 dependencies {
 ～
     testRuntime group: 'jp.hishidama.asakusafw', name: 'asakusafw-tester', version: '0.+'
 ～
 }


使用例
------
| Asakusaサンプルの ``CategorySummaryOperator`` の ``joinItemInfo`` メソッド（ ``@MasterJoin`` ）をテストする例です。
| 全体像は `CategorySummaryOperatorTest.java <https://github.com/hishidama/asakusafw-tester/blob/master/CategorySummaryOperatorTest.java>`_ を見て下さい。

.. sourcecode:: java

   import jp.hishidama.asakusafw.tester.MasterJoinTester;
   import jp.hishidama.asakusafw.tester.MasterJoinTester.MasterJoinResult;

.. sourcecode:: java

   // Tester作成
   MasterJoinTester tester = new MasterJoinTester(CategorySummaryOperator.class, "joinItemInfo");

   // 入力データ作成
   List<ItemInfo> master = new ArrayList<>();
   ～
   List<SalesDetail> tx = new ArrayList<>();
   ～
 
   // テストの実行
   MasterJoinResult<JoinedSalesInfo, SalesDetail> result = tester.execute(master, tx);

   // 実行結果の検査
   List<JoinedSalesInfo> joined = result.joined;
   ～
   List<SalesDetail> missed = result.missed;
   ～

テストしたい演算子（アノテーション）の種類に応じたTesterを使用します。
Testerのコンストラクターにはテスト対象のOperatorクラスとメソッド名を指定します。
（Tester内部でOperatorImplのインスタンスを生成します）

そして、 executeメソッドに入力データを渡すと、演算子の種類に応じて入力データをグルーピングしてメソッドを呼び出します。
戻ってきたデータをassertThat等で検査して下さい。

※AsakusaFWでは、ソートは入力データに対して行われるものであり、出力結果のソート順は保証されていません。
当テストツールとしてはなるべく入力された順序を保っていますが、出力結果の確認の際は、順序に依存しないように検査した方が無難です。

