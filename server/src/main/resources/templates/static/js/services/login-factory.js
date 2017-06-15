rbcRobotModule.factory('LoginPathFactory', function(){
  return {
		login: function(){ return '/login';},
		logout: function(id){ return '/logout';}
	};
});

rbcRobotModule.factory('LoginFactory', ['$http', '$q', 'LoginPathFactory', function($http, $q, LoginPathFactory){
  var factory = {
    login: function(username, password) {
      var deferred = $q.defer();
      $http({
          method: 'POST',
          url: LoginPathFactory.login(),
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          transformRequest: function(obj) {
              var str = [];
              for(var p in obj)
              str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
              return str.join("&");
          },
          data: {username: username, password: password}
      }).success(function () {
        deferred.resolve(response);
      });

      // $http.post(LoginPathFactory.login(), {username: username, password: password}).then(function(response){
      //   deferred.resolve(response);
      // }, function(response){
      //   deferred.reject(response);
      // });
      return deferred;
    },

    logout: function() {
      var deferred = $q.defer();
      $http.get(LoginPathFactory.login()).then(function(response){
        deferred.resolve(response);
      }, function(response){
        deferred.reject(response);
      });
      return deferred;
    }
  };

  return factory;
}]);
