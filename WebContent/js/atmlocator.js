/**
 * 
 */

var atmlocator = angular.module('atmlocator', ['ui.bootstrap', 'ngMessages', 'smart-table']);

angular.isEmpty = function(val) {
    return angular.isUndefined(val) || val === null || val === ''
}

atmlocator.controller('ATMSearchController', ['$scope', '$http', function($scope, $http){
	
	$scope.cityName='';
	$scope.resultData='';
	$scope.errorMessage='';
	$scope.recordCount='';
	$scope.pageCount='';
	function showSuccessResult (response){
		
		if (response.data != ''){
			$scope.resultData = response.data;
			$scope.recordCount= response.data.length;
			$scope.pageCount=$scope.recordCount/10;
			if ($scope.recordCount%10 != 0){
				$scope.pageCount = $scope.pageCount + 1;
			}
		} else {
			$scope.errorMessage = 'No ING ATM found in ' + $scope.cityName;
			console.log("errorMessage : " + $scope.errorMessage);
		}
		
	};
	
	$scope.showErrorResult = function(response){
		console.log("response : " +response);
	};
	
	$scope.searchATM = function(cityName){
		$scope.cityName = cityName;
		$scope.errorMessage='';
		$scope.resultData='';
		$scope.recordCount='';
		$scope.pageCount='';
		console.log("Search ATM for " + cityName);
		var baseURL = 'http://localhost:8080/ATMLocator/services/searchATM/getATMByCity?city=';
		var searchURL = baseURL+encodeURI(cityName);
		console.log("Fetch data using URL : " + searchURL);
		var customHeaders = {'ATM_LOCATOR_USER' : 'backbase', 'ATM_LOCATOR_PWD' : 'backbase', 'ATM_LOCATOR_ROLE' : 'searchATM'};
		
		$http.get(searchURL, {headers : customHeaders}).then(function(response){
			showSuccessResult(response);
		} , function(response){
			var errorText = 'Unable to search ATM for ' + $scope.cityName;
			if (401 === response.status){
				errorText = errorText + '; Unauthorized access';
			}
			$scope.errorMessage = 'Unable to search ATM for ' + $scope.cityName;
		});
	};
	
	
}] );

