package cms.beans;

public enum ClientType {
	ADMIN {
		public String toString() {
			return "ADMIN";
		}
	},

	COMPANY {
		public String toString() {
			return "COMPANY";
		}
	},

	CUSTOMER {
		public String toString() {
			return "CUSTOMER";
		}
	}
}
