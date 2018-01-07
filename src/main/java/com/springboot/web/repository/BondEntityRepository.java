package com.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.web.entity.BondEntity;
@Repository
public interface BondEntityRepository extends JpaRepository<BondEntity, Integer> {

	/** 銘柄コードでBondEntityを検索するメソッド
	 *
	 * @param bondCode 銘柄コード
	 * @return List<BondEntity>
	 */
	public List<BondEntity> findByBondCode(String bondCode);
}
