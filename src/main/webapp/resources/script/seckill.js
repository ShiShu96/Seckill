var seckill = {
    //封装URL
    URL: {
        now: function () {
            return '/time/now';
        },
        exposer: function (seckillId) {
            return '/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/' + seckillId + "/" + md5 +'/execution';
        }

    },
    handleSeckillkill : function(seckillId, node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        console.log("hello");
        $.post(seckill.URL.exposer(seckillId),{}, function (result) {
            console.log("baby");
            if(result&&result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    var md5 = exposer['md5'];
                    var killURL = seckill.URL.execution(seckillId, md5);
                    console.log("killURL: " + killURL);
                    $('#killBtn').one('click', function () {
                        console.log("before disabled")
                        $(this).addClass('disabled');//点击之后disable这个按钮
                        console.log("after disabled");
                        $.post(killURL,{},function (result) {
                            console.log("execute post function");
                            if(result&&result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                               // node.html('傻宝宝');
                            }
                        });
                    });
                    node.show();
                }else {
                    console.log("where is the error?");
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end );
                }
            }else{
                console.log('result:' + "result:")
            }
        })
    },
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone))
            return true;
        else
            return false;

    },

    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');

        if (nowTime > endTime) {
            seckillBox.html('秒杀结束！');
        }else if(nowTime < startTime){
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时： %D 天 %H时 %M分 %S秒 %F');
                seckillBox.html(format);
            }.on('finish.countdown',function () {
                seckill.handleSeckillkill(seckillId, seckillBox);
            }));
        }else{
            seckill.handleSeckillkill(seckillId, seckillBox);
        }

    },
    //详情页秒杀逻辑
    detail: {
        init: function (params) {

            var killPhone = $.cookie('killPhone');

            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log(inputPhone);
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/'});
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });

            }

            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result = ' + result);
                }
            });

        }
    }
}