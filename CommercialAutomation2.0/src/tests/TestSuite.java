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
	ReportsSaleTest.class,
	ReportsStockTest.class,
})
class TestSuite {
};
