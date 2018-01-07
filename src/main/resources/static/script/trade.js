document.addEventListener('DOMContentLoaded', function() {
  console.log("load trade.js");
  inputTradeForm = document.getElementById('inputtradeform');
  if(inputTradeForm!=null) {
    // 新規登録ボタンが押された時にバリデーションを実施し、確認ダイアログを表示する
    confirmInputTrade(inputTradeForm);
  }
  // 取引の訂正ボタンが押されたとき
  sendUpdateRecord();
}, false);

// 取引の新規登録メソッド
var confirmInputTrade = function(inputTradeForm) {
  inputTradeForm.addEventListener('submit', function(e) {
    var bondcode = document.getElementById('bondcode').value;
    var buysell = document.getElementById('buysell').value;
    var tradeamount = document.getElementById('tradeamount').value;
    var tradevalue = document.getElementById('tradevalue').value;
    var tradetime = document.getElementById('tradetime').value;

    // 新規入力のボタンが押された時に未入力の項目が無いかのバリデーション
    var blankFlag = inputValidate(bondcode, buysell, tradeamount, tradevalue, tradetime);
    if(!blankFlag){
      // 空欄があったら警告し、サーバーに送信しない
      e.preventDefault();
      window.alert('Filled All Blank.');
    } else{
      // 確認メッセージを作成する
      var message = createInputTradeConfirmMessage(bondcode, buysell, tradeamount, tradevalue, tradetime);
      console.log(message);
      // 確認ダイアログを表示する
      if(window.confirm(message)){
        window.alert('Complete input new Trade!')
        console.log('Sent new TRADE to Server.');
      } else {
        // 確認画面でfalseだったらサーバーに送信しない
        e.preventDefault();
        window.alert('Canceled input new Trade.')
        console.log('Canceled send new TRADE to Server.');
      }
    }
  }, false);

}

// 空欄が無いかのバリデーション
var inputValidate = function(bondcode, buysell, tradeamount, tradevalue, tradetime) {
  if(bondcode==''){
    return false;
  } else if(buysell==''){
    return false;
  } else if(tradeamount==''){
    return false;
  } else if(tradevalue==''){
    return false;
  } else if(tradetime==''){
    return false;
  }else {
    return true;
  }
}

// tradeの新規登録時の確認メッセージを作成する
var createInputTradeConfirmMessage = function(bondcode, buysell, tradeamount, tradevalue, tradetime) {
  var message = '以下の取引を登録します。よろしいですか。\n銘柄名称:' + bondcode
  + '\n売買:' + buysell + '\n取引数量:' + tradeamount + '\n取引価格:'
  +  tradevalue + '\n取引時刻:' + tradetime;
  return message;
}

// UPDATEボタンが押下されたときにサーバーに一覧の対象の取引の取引コードを送信する
var sendUpdateRecord = function() {
  // すべてのUPDATEボタンにイベントリスナーを設定
  var updatebtn = document.getElementsByName('updatebtn');
  for(var i=0, l=updatebtn.length;i<l;i++){
    updatebtn.item(i).addEventListener('click', function(e) {
      // UPDATEボタンが押されたカラムを取得
      var column = e.target.parentNode.parentNode;  // <td></td>
      // tradecodeを取得
      var tradecodeElement = column.firstElementChild.nextElementSibling.nextElementSibling;
      //　サーバーにtradecodeを送信する
      document.getElementById('listform').name = tradecodeElement.textContent;
      document.getElementById('listform').submit();
    }, false);
  }
};
