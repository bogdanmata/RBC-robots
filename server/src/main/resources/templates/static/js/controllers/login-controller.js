rbcRobotModule.controller('LoginController', [ '$scope', '$location', 'LoginFactory', function($scope, $location, LoginFactory) {

  $scope.username = '';
  $scope.password = '';

  $scope.errorMessage = '';

  $scope.submit = function() {
    LoginFactory.login($scope.username, $scope.password).then(function(loggedUser) {
      $scope.errorMessage = '';
      $location.path('/');
    }, function(error) {
      $scope.errorMessage = error.errorMessage;
      console.log(msg);
    });
  };

  $scope.reset = function() {
    $scope.username = '';
    $scope.password = '';
  };
} ]);
