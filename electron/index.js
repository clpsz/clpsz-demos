var http = require('http');
var querystring = require('querystring');



if (document.getElementById("parent_pay_order_id_list").value == "") {
    document.getElementById("parent_pay_order_id_list").value = "PPO20170303000052400555";
}




function preparePostData(mysqlType, db, tbl, keyName, key, shardingBy) {
    var set, db_seq, tbl_seq;
    if (shardingBy == "right5") {
        set = key.substr(-5, 2);
        db_seq = key.substr(-3, 2);
        tbl_seq = key.substr(-1, 1);
    } else if (shardingBy == "uid") {
        var set = "";
        if (key >= 10000000) {
            set = "01";
        } else {
            set = "00";
        }
        db_seq = key.substr(-3, 2);
        tbl_seq = key.substr(-1, 1);
    }

    var table = db + "_" + set + "_" + db_seq + "_db." + tbl + "_" + tbl_seq;
    var sql = "select * from " + table + " where " + keyName + " = '" + key + "'";

    var postData = querystring.stringify({
        'mysql_type': mysqlType,
        'sql': sql
    });

    return postData;
}


function prepareOptions(postData) {
    return {
        hostname: 'zeus.oa.fenqile.com',
        path: '/mysql/sql_exec_api/',
        method: 'POST',
        headers: {
            "Accept": "*/*",
            "Accept-Encoding": "utf8",
            "Accept-Language": "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4",
            "Cache-Control": "no-cache",
            "Connection": "keep-alive",
            "Content-Length": Buffer.byteLength(postData),
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            "Cookie": "_SUTC=6c074b48eb7604817a44e21c8e284fcb9437de4a; fs_tag=EB4545ED17E98ADD58F57C5709BEBD34; Hm_lvt_fdfea51f5530d9b1730875677c8b0ca8=1474549244,1474948540,1475231359,1476085327; fs_channel=9F1A331-62CF726; oa_token_id=oWJ67Rk9AxyjOS8Jegauod4aPV%2F7Ks8RsDHa%2BbmuK9TYNTAjGyBn4Nmn%2F6oU7%2FIFprW0wbuM0g%2FcEbnU3Wd3tg%3D%3D; uid=3004772; Hm_lvt_3592cc1c1f867054320b2773e17711c4=1488524804; event_id=EVE201608040000252_EMP201703010004826_limit; oa_session=j9bd95vgkseifl0doh7ss9eop4; www_token_id=DEXXry6DwdbcV7OafIbTx4n0gD7l6f32JSMkmqPYitV%2BGQxN4%2FOY0yHmozrS4rdHeakf%2B12nro%2FKG2KcTiVDhA%3D%3D; session=7ge67odl0qrlbn389bm6cogko6; sessionid=tijdv65u7lfjztusa04ao3z46k18wugx",
            "Host": "zeus.oa.fenqile.com",
            "Origin": "http://zeus.oa.fenqile.com",
            "Pragma": "no-cache",
            "Referer": "http://zeus.oa.fenqile.com/mysql/sql_exec/",
            "User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest"
        }
    };
}

function doGetPostXjaxResponse(postData, options) {
    return new Promise(function (resolve, reject) {

        var req = http.request(options, (res) => {
            res.setEncoding('utf8');

            var html = '';
            res.on('data', (chunk) => {
                html += chunk;
            });
            res.on('end', () => {
                resolve(html)
            });

        });

        req.on('error', (e) => {
            reject(e)
        });

        req.write(postData);
        req.end();
    });
}

function getPostXjaxResponse(db, tbl, keyName, key) {
    var mysqlType = '';
    var shardingBy = '';
    if (db == "pay_order") {
        mysqlType = 'PayodrDB';
        shardingBy = 'right5';
    } else if (db == "user_info") {
        mysqlType = 'UserDB';
        shardingBy = 'uid';
    } else if (db == "parent_order") {
        mysqlType = 'ParentOrderDB';
        shardingBy = 'right5';
    }


    postData = preparePostData(mysqlType, db, tbl, keyName, key, shardingBy);
    options = prepareOptions(postData);

    return doGetPostXjaxResponse(postData, options);
}


function mySetTimeout(key, parentPayOrderId) {
    setTimeout(function () {
        getPostXjaxResponse("pay_order", "t_parent_pay_order", "Fparent_pay_order_id", parentPayOrderId).then(
            function (response) {
                var json = JSON.parse(response);
                var data = json['data'][0];
                var res = JSON.stringify(data);
                var firstpayAmount = data['Ffirstpay_amount'].toString();
                var loanAmount = data['Floan_amount'].toString();
                var uid = data['Fuid'].toString();
                var payWay = data['Fpay_way'].toString();
                console.log(parentPayOrderId, uid, loanAmount, firstpayAmount);

                document.getElementById("query_result").value = res;

                return getPostXjaxResponse("user_info", "t_user_info", "Fuid", uid);
            }, function (error) {
                console.error("请求处理失败");
            }
        ).then(function (response) {
            var json = JSON.parse(response);
            var data = json['data'][0];
            var res = JSON.stringify(data);

            curValue = document.getElementById("query_result").value;
            newValue = curValue + res;
            document.getElementById("query_result").value = newValue;
        })
    }, key * 50)
}

clickButton = document.getElementById("click-button");
clickButton.addEventListener('click', function () {
    var list = document.getElementById("parent_pay_order_id_list").value;
    var breakList = list.split(",");
    for (key in breakList) {
        var parentPayOrderId = breakList[key];
        mySetTimeout(key, parentPayOrderId)
    }
});
