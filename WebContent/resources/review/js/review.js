
function init (el,a,bg) {
    var context = el.getContext('2d'),  //获取画图环境，指明为2d
        centerX = el.width/2,   //Canvas中心点x轴坐标
        centerY = el.height/2,  //Canvas中心点y轴坐标
        rad = Math.PI*2/100, //将360度分成100份，那么每一份就是rad度
        speed = 0.1, //加载的快慢就靠它了
        r = 17; //进度icon 的半径

    //绘制运动外圈
    function blueCircle(n){
        context.save();
        context.strokeStyle = bg; //设置描边样式
        context.lineWidth = 11; //设置线宽
        context.beginPath(); //路径开始
        context.arc(centerX, centerY, centerX - 11 , -Math.PI/2, -Math.PI/2 + n*rad * 10, false); //用于绘制圆弧context.arc(x坐标，y坐标，半径，起始角度，终止角度，顺时针/逆时针)
        context.stroke(); //绘制
        context.closePath(); //路径结束
        context.restore();
    }
    //绘制白色外圈
    function whiteCircle(){
        context.save();
        context.beginPath();
        context.lineWidth = 11; //设置线宽
        context.strokeStyle = "#F5F5F5";
        context.arc(centerX, centerY, centerX - 11 , 0, Math.PI*2, false);
        context.stroke();
        context.closePath();
        context.restore();
    }
    //绘制圆形icon
    function pos (r, n) {
        var radian = (n * 10) / 100 * 2 * Math.PI - 0.5 * Math.PI;
        var x = Math.cos(radian) * (centerX - r + 6) + centerX;
        var y = Math.sin(radian) * (centerX - r + 6) + centerX;
        context.save();
        context.beginPath();
        context.fillStyle = "#FFFFFF";
        context.shadowBlur = 2;
        context.shadowColor = bg;
        context.arc(x, y, 0.6 * r, 0, 2 * Math.PI, false);

        context.fill();
        context.restore();
    }
    //百分比文字绘制
    function text(n){
        var num = 0;

        n >= 9.96?num = n.toFixed(0):num = n.toFixed(1);
        context.save(); //save和restore可以保证样式属性只运用于该段canvas元素
        context.beginPath();
        context.fillStyle = "#000"; //设置描边样式
        context.font = "60px SegoeUI-Light"; //设置字体大小和字体
        //绘制字体，并且指定位置
        context.fillText(num, centerX-38, centerY+20);
        context.stroke(); //执行绘制
        context.restore();
    }
    //动画循环
    function drawFrame(a){
        var timer = setInterval(function () {
            context.clearRect(0, 0, canvas.width, canvas.height);

            whiteCircle();

            blueCircle(speed);

            text(speed);

            pos (r, speed);

            speed += 0.04;

            if (speed > a) {
                clearInterval(timer);
            }
        })
    }



    drawFrame(a);
}

function addClassFn (el,a,b) {
    $(el + ' .' + a).find('li').each(function (i) {
        if (i%2 === 0 && i !== 0) {
            $(this).addClass('gray');
        }
    });

    $(el + ' .' + b).find('li').each(function (i) {
        if (i%2 === 0 && i !== 0) {
            $(this).addClass('gray');
        }
    });
}

$(function () {
    var canvas = document.getElementById('canvas'), //获取canvas元素
        canvas2 = document.getElementById('canvas2'),
        canvas3 = document.getElementById('canvas3');

    init (canvas,canvas.getAttribute('data-score'),canvas.getAttribute('data-bg'));
    init (canvas2,canvas2.getAttribute('data-score'),canvas2.getAttribute('data-bg'));
    init (canvas3,canvas3.getAttribute('data-score'),canvas3.getAttribute('data-bg'));
})

window.onload = function(){


    addClassFn ('.shop','TOP10','LOW10');

    addClassFn ('.therapist','TOP10','LOW10');

    addClassFn ('.treatment','TOP10','LOW10');

    $('.star').each(function () {
        var color = $(this).attr('data-bg');
        $(this).raty({
            width: 90,

            space : false,

            readOnly: true,

            half     : true,

            starHalf : './star-half-'+ color +'.svg',

            starOff  : './star-off.svg',

            starOn   : '/star-on-'+ color +'.svg',

            score: function() {
                return $(this).attr('data-score');
            }
        });
    });

    $('.rat-title').find('a').click(function () {
        $(this).parent().parent().hide().siblings().show();
    });
}