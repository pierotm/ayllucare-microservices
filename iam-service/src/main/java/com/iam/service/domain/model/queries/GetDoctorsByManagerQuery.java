package com.iam.service.domain.model.queries;

/**
 * Query to get all doctors by a specific manager.
 * <p>
 *     This query is used to get all doctors created by a specific manager.
 * </p>
 */
public record GetDoctorsByManagerQuery(Long managerId) {
}
