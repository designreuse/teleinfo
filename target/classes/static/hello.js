function Hello($scope, $http) {
    $http.get('hello-world').
        success(function(data) {
            $scope.greeting = data;
        });
}

function GetCustomers($scope, $http) {
    $http.get('customer/all').
        success(function(data) {
            $scope.customers = data;
        });
}