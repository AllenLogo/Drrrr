<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/default.css">
  	<link href="css/cover.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<link href="lib/css/nanoscroller.css" rel="stylesheet">
	<link href="lib/css/emoji.css" rel="stylesheet">
</head>
<body>
	<div class="htmleaf-container">
		<div class="site-wrapper">
		    <div class="site-wrapper-inner">
		      <div class="cover-container">

		        <div class="inner cover">

		          <p class="lead emoji-picker-container">
		            <textarea id="textmsg" class="form-control textarea-control" rows="3" placeholder="Textarea" data-emojiable="true"></textarea>
		          </p>

		          <input type="button" value="提交" onclick="getValue()" />

		        </div>

		      </div>

		    </div>
		  </div>
	</div>
	
	<script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-2.1.1.min.js"><\/script>')</script>
	<!-- Begin emoji-picker JavaScript -->
	<script src="lib/js/nanoscroller.min.js"></script>
	<script src="lib/js/tether.min.js"></script>
	<script src="lib/js/config.js"></script>
	<script src="lib/js/util.js"></script>
	<script src="lib/js/jquery.emojiarea.js"></script>
	<script src="lib/js/emoji-picker.js"></script>
	<!-- End emoji-picker JavaScript -->
	<script>
	function getValue(){
		content = $("#textmsg").val();
		alert(content);
	}
    $(function() {
      window.emojiPicker = new EmojiPicker({
        emojiable_selector: '[data-emojiable=true]',
        assetsPath: 'lib/img/',
        popupButtonClasses: 'fa fa-smile-o'
      });
      window.emojiPicker.discover();
    });
  	</script>
</body>
</html>