package com.rvrs.bestie.core.audit.domain;

import org.hibernate.envers.RevisionType;

public enum OperationType {
	CREATE, UPDATE, DELETE;

	public static OperationType getOperationTypeFor(RevisionType revisionType) {
		return switch (revisionType) {
			case ADD -> CREATE;
			case MOD -> UPDATE;
			case DEL -> DELETE;
		};
	}
}
