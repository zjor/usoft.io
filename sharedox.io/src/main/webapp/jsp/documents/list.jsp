<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>ShareDox</title>

	<link rel="stylesheet" href="${baseURL}/static/css/reset.css">
	<link rel="stylesheet" href="${baseURL}/static/fonts/fonts.css">
	<link rel="stylesheet" href="${baseURL}/static/css/style.css">

	<script src="${baseURL}/static/js/jquery.min.js"></script>
	<script src="${baseURL}/static/js/json2.js"></script>
	<script src="${baseURL}/static/js/underscore-min.js"></script>
	<script src="${baseURL}/static/js/backbone-min.js"></script>
	<script src="${baseURL}/static/js/dropzone.js"></script>

</head>
<body>

<div id="fb-root"></div>
<script>(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=735092636525455";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="clearfix header">
	<div class="left">
		<img src="${baseURL}/static/images/sharedox_logo.png">
	</div>
	<div class="left title">
		<h1>ShareDox</h1>
		<h2>simply.share.</h2>
	</div>

	<div class="right title">
		<div class="clearfix">
			<div class="left">
				<img class="avatar"
					 src="${it.profile.avatarURL}"/>
			</div>
			<div class="right">
				<div>${it.profile.firstName} ${it.profile.lastName}</div>
				<div class="right"><a href="#">Logout</a></div>
			</div>
		</div>
	</div>
</div>

<div class="container">

	<div><div class="dropzone" id="my-dropzone"></div></div>
	<div id="add-form-container" style="display: none;">
		<div class="item-container">
			<h2 id="add-form-filename">Filename: </h2>
			<div>
				<input class="title" type="text" placeholder="Title" name="title"/>
			</div>
			<div>
				<textarea class="description" placeholder="Description" name="description"></textarea>
			</div>
			<div class="clearfix">
				<a href="#" class="btn save" id="add-form-submit">Save</a>
				<a href="#" class="btn remove" id="add-form-remove">Remove</a>
			</div>
		</div>
	</div>

	<h1 id="documents-title">Recently uploaded documents</h1>
	<div id="documents-list"/>

</div>

<script type="text/x-template" id="document-item">
	<div class="clearfix">
		<div class="left"><img src="${baseURL}/static/images/document.png"></div>
		<div class="left description-container">
			<h1><@= title @></h1>
			<p><@= description @></p>
		</div>
		<div class="delete-button">Delete</div>
	</div>
	<div class="url"><a href="<@= url @>"><@= url @></a></div>
	<div class="clearfix">
		<div class="left"><@= creation_date @></div>
		<div class="right">Downloads: <@= downloads @></div>
	</div>
</script>

<script>
	_.templateSettings = {
		interpolate: /\<\@\=(.+?)\@\>/gim,
		evaluate: /\<\@([\s\S]+?)\@\>/gim,
		escape: /\<\@\-(.+?)\@\>/gim
	};
	Dropzone.autoDiscover = false;

	$(function() {
		var Document = Backbone.Model.extend({
			idAttribute: 'id',
			defaults: {
				title: '',
				description: '',
				url: '',
				creation_date: '',
				downloads: 0
			}
		});

		var Documents = Backbone.Collection.extend({
			model: Document,
			url: '${baseURL}/api/json/documents'
		});

		var DocumentView = Backbone.View.extend({
			tagName: 'li',
			className: 'item-container',
			template: template('document-item'),
			events: {
				'click .delete-button': 'delete'
			},
			render: function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},
			delete: function() {
				var id = this.model.get('id');
				var view = this.$el;
				var model = this.model;
				$.ajax({
					url: '${baseURL}/api/documents/delete/' + id,
					type: 'DELETE',
					success: function() {
						view.slideUp(300, function() {
							docs.remove(model);
						});
					}
				});
			}
		});

		var ListView = Backbone.View.extend({
			tagName: 'ul',
			className: 'documents',
			initialize: function(options) {
				this.model.on('add', this.render, this);
				this.model.on('change', this.render, this);
				this.model.on('remove', this.render, this);
			},
			render: function() {
				var el = this.$el;
				el.empty();
				this.model.each(function(item) {
					var view = new DocumentView({model: item});
					el.append(view.render().el);
				});
				return this;
			}
		});

		var docs = new Documents();
		var listView = new ListView({model: docs});
		docs.fetch();
		$('#documents-list').html(listView.render().el);

		var dropzone = new Dropzone('#my-dropzone',
				{
					url: '${baseURL}/api/upload',
					previewsContainer: null
				});
		dropzone.on('success', function(file, response) {
			$('#my-dropzone').hide();
			var addFormContainer = $('#add-form-container');
			addFormContainer.show();

			$('#add-form-filename').html('Filename: ' + response.filename);
			$('input[name="title"]').val(response.filename);
			$('#add-form-submit').click(function() {
				$.post('${baseURL}/api/documents/create',
						{
							resource_location: response.resource_location,
							title: $('input[name="title"]').val(),
							description: $('textarea[name="description"]').val(),
							filename: response.filename
						}, function() {restoreDropzone(); docs.fetch();}
				);

			});
			$('#add-form-remove').click(restoreDropzone);
			console.log(response.filename);
			console.log(response.resource_location);

		});

		function restoreDropzone() {
			var addFormContainer = $('#add-form-container');
			addFormContainer.hide();
			dropzone.removeAllFiles(true)
			$('#my-dropzone').show();
		}

	});

	function template(templateId) {
		return _.template($('#' + templateId).html());
	};

</script>

</body>
</html>