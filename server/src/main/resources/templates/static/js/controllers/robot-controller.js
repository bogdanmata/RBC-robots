rbcRobotModule.controller('RobotListController', [ '$scope', '$location', 'RobotFactory', 'robots', function($scope, $location, RobotFactory, robots) {
  $scope.robots = robots;
  $scope.deleteRobot = function(id) {
    RobotFactory.deleteRobot(id).then(function(response) {
      // should be better a remove from the list
      _.remove($scope.robots, function(robot){
        return robot.id == response.id;
      });
    }, function(error) {
      $scope.errorMessage = error.data.errorMessage;
    });
  };
  $scope.errorMessage = '';
} ]);

rbcRobotModule.controller('RobotCreateController', [ '$scope', '$location', 'RobotFactory', function($scope, $location, RobotFactory) {
  resetForm = function() {
    $scope.robot = {};
    $scope.errorMessage = '';
  };

  $scope.submit = function() {
    RobotFactory.addRobot($scope.robot).then(function(response) {
      $location.path('/robot');
    }, function(error) {
      $scope.errorMessage = error.data.errorMessage;
    });
  };
} ]);

rbcRobotModule.controller('RobotController', [ '$scope', '$location', 'RobotFactory', 'robot', function($scope, $location, RobotFactory, robot) {
  $scope.robot = robot;
  $scope.deleteRobot = function() {
    RobotFactory.deleteRobot($scope.robot.id).then(function(response) {
      $location.path('/robot');
    }, function(error) {
      $scope.errorMessage = error.data.errorMessage;
    });
  };

  $scope.errorMessage = '';

} ]);
