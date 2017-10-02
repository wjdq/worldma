// 文件上传
jQuery(function() {
    var h = window.location.host;
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({

        // 不压缩image
        resize: false,
        // swf文件路径
        // swf: BASE_URL + '/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://'+ h +'/manage/add_img_activity_msg',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
        fileVal:"file",//设置文件上传域的name
        fileSizeLimit:18 * 1024 * 1024,//所有文件上传的大小限制,单位字节
        fileSingleSizeLimit:6 * 1024 * 1024,//单张图片上传限制大小，单位字节
        fileNumLimit:3,
        threads : 1
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.append( '<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file, response) {
        $( '#'+file.id ).find('p.state').text('已上传');
        $('#activityMsg').modal('hide')
        $("#ImgTextMsg").append("<tr><td class=\"text-center\">" + response.data.id + "</td><td>" + response.data.msg + "</td><td class=\"text-center\">图片消息</td><td class=\"text-center\"><button type=\"button\" class=\"btn btn-success btn-xs msg-status\" value=\"1\">允许发送</button></td><td class=\"text-center\">最新发布</td><td class=\"text-center\"><button type=\"button\" class=\"btn btn-danger btn-xs delete_msg\" value=\""+ response.data.id+"\">删除</button></td></tr>");

    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});