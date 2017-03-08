var http = require('http');
var querystring = require('querystring');


clickButton = document.getElementById("click-button");


clickButton.addEventListener('click', function ()
{
    var postData = querystring.stringify({
            'mysql_type': 'PayodrDB',
            'sql': "select * from pay_order_00_55_db.t_parent_pay_order_5 where fparent_order_id = 'fo20170303004706000555'"
    });
    var options = {
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

    var req = http.request(options, (res) => {
        console.log(`STATUS: ${res.statusCode}`);
        console.log(`HEADERS: ${JSON.stringify(res.headers)}`);
        res.setEncoding('utf8');
        
        var html = '';
        res.on('data', (chunk) => {
            html += chunk;
            // console.log(`BODY: ${chunk}`);
        });
        res.on('end', () => {
            console.log(html);

            var parentPayOrderId = document.getElementById("parent_pay_order_id").value;

            var json = JSON.parse(html);
            
            document.getElementById("query_result").value= JSON.stringify(json['data'][0]);
        });

    });

    req.on('error', (e) => {
    console.log(`problem with request: ${e.message}`);
    });

    // write data to request body
    req.write(postData);
    req.end();
});

