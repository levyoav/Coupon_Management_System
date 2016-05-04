package cms.beans;

public enum CouponType {
	RESTAURANTS {
		public String toString() {
			return "RESTAURANTS";
		}
	},
	
	ELECTRICITY {
		public String toString() {
			return "ELECTRICITY";
		}
	},
	
	FOOD {
		public String toString() {
			return "FOOD";
		}
	},
	
	HEALTH {
		public String toString() {
			return "HEALTH";
		}
	},
	
	SPORTS {
		public String toString() {
			return "SPORTS";
		}
	},
	CAMPING {
		public String toString() {
			return "CAMPING";
		}
	},
	
	TRAVELLING {
		public String toString() {
			return "TRAVELLING";
		}
	}
}
