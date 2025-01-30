package com.minin.infrastructure.extension.db

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> dbQuery(block: () -> T) = transaction { block() }

fun <R> ResultRow.map(block: (ResultRow) -> R): R = block(this)

fun List<Op<Boolean>>.toWhereClause(): Op<Boolean> {
    return if (this.isEmpty()) {
        Op.TRUE
    } else {
        this.reduce { acc, op -> acc and op }
    }
}
