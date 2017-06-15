rbcRobotModule.factory('LoginPathFactory', function(){
  return {
		login: function(){ return '/__service/login';},
		logout: function(id){ return '/__service/logout';}
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
      }).then(function (response) {
        deferred.resolve(response.data);
      }, function(error) {
        deferred.reject(error.data);
      });

      return deferred.promise;
    },

    logout: function() {
      var deferred = $q.defer();
      $http.get(LoginPathFactory.logout()).then(function(response){
        deferred.resolve(response.data);
      }, function(error){
        deferred.reject(error.data);
      });
      return deferred.promise;
    }
  };

  return factory;
}]);
