package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	ManagementTest.class,
	ManagementMenuTest.class,
	ManagementProductsTest.class,
	ManagementProvidersTest.class,
	ManagementSalesTest.class,
	ManagementUsersTest.class,
	EntitiesTest.class,
	ItemsTest.class,
	ProductsTest.class,
	ProvidersTest.class,
	SalesTest.class,
	UsersTest.class,
	ReportsProviderTest.class,
<<<<<<< HEAD
	ReportsStockTest.class,
	ReportsSaleTest.class,
	
=======
	ReportsSaleTest.class,
	ReportsStockTest.class,
>>>>>>> c14ff6084adc688fd3363d069cfdad0c9d78dca3
})
class TestSuite {
};
