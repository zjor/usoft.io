package io.usoft.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sergey Royz
 * Date: 14.03.2014
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

	private String databaseName;
	private String collectionName;

}
