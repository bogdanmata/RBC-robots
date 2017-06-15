rbcRobotModule.controller('HeaderController', [ '$rootScope', '$scope', '$location', 'LoginFactory', function($rootScope, $scope, $location, LoginFactory) {
  $scope.currentUser = null;
  
  $rootScope.$on('UserLoginEvent', function(event, loggedUser){
    $scope.currentUser = loggedUser;
  });
  
  $scope.logout = function() {
    LoginFactory.logout().then(function() {
      $scope.currentUser = null;
      $location.path('/');
    }, function(error) {
      console.log(msg);
    });
  };
  
} ]);