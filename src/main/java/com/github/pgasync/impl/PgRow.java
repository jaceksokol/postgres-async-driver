/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pgasync.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Map;

import com.github.pgasync.Row;
import com.github.pgasync.SqlException;
import com.github.pgasync.impl.message.DataRow;

public class PgRow implements Row {

	final DataRow data;

	Map<String,PgColumn> columns;
	PgColumn[] pgColumns;

	public PgRow(DataRow data) {
		this.data = data;
	}

	public void setColumns(Map<String,PgColumn> columns) {
		this.columns = columns;
		Collection<PgColumn> values = columns.values();
		this.pgColumns = values.toArray(new PgColumn[values.size()]); 
	}

	@Override
	public String getString(int index) {
		return TypeConverter.toString(pgColumns[index].type, data.getValue(index));
	}
	@Override
	public String getString(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toString(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Character getChar(int index) {
		return TypeConverter.toChar(pgColumns[index].type, data.getValue(index));
	}
	@Override
	public Character getChar(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toChar(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Byte getByte(int index) {
		return TypeConverter.toByte(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Byte getByte(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toByte(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Short getShort(int index) {
		return TypeConverter.toShort(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Short getShort(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toShort(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Integer getInt(int index) {
		return TypeConverter.toInteger(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Integer getInt(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toInteger(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Long getLong(int index) {
		return TypeConverter.toLong(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Long getLong(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toLong(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public BigInteger getBigInteger(int index) {
		return TypeConverter.toBigInteger(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public BigInteger getBigInteger(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toBigInteger(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public BigDecimal getBigDecimal(int index) {
		return TypeConverter.toBigDecimal(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public BigDecimal getBigDecimal(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toBigDecimal(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Date getDate(int index) {
		return TypeConverter.toDate(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Date getDate(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toDate(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public Time getTime(int index) {
		return TypeConverter.toTime(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public Time getTime(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toTime(pgColumn.type, data.getValue(pgColumn.index));
	}

	@Override
	public byte[] getBytes(int index) {
		return TypeConverter.toBytes(pgColumns[index].type, data.getValue(index));
	}

	@Override
	public byte[] getBytes(String column) {
		PgColumn pgColumn = getColumn(column);
		return TypeConverter.toBytes(pgColumn.type, data.getValue(pgColumn.index));
	}

	PgColumn getColumn(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Column name is required");
		}
		PgColumn column = columns.get(name.toUpperCase());
		if(column == null) {
			throw new SqlException("Unknown column '" + name + "'");
		}
		return column;
	}
}