// Generated from Expr.g4 by ANTLR 4.7.1
package acide.process.parser.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(ExprParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(ExprParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#sql_stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterSql_stmt_list(ExprParser.Sql_stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#sql_stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitSql_stmt_list(ExprParser.Sql_stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#sql_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSql_stmt(ExprParser.Sql_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#sql_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSql_stmt(ExprParser.Sql_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#alter_table_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAlter_table_stmt(ExprParser.Alter_table_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#alter_table_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAlter_table_stmt(ExprParser.Alter_table_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#analyze_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAnalyze_stmt(ExprParser.Analyze_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#analyze_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAnalyze_stmt(ExprParser.Analyze_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#attach_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAttach_stmt(ExprParser.Attach_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#attach_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAttach_stmt(ExprParser.Attach_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#begin_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBegin_stmt(ExprParser.Begin_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#begin_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBegin_stmt(ExprParser.Begin_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#commit_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCommit_stmt(ExprParser.Commit_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#commit_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCommit_stmt(ExprParser.Commit_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#rollback_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRollback_stmt(ExprParser.Rollback_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#rollback_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRollback_stmt(ExprParser.Rollback_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#savepoint_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSavepoint_stmt(ExprParser.Savepoint_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#savepoint_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSavepoint_stmt(ExprParser.Savepoint_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#release_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRelease_stmt(ExprParser.Release_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#release_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRelease_stmt(ExprParser.Release_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#create_index_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_index_stmt(ExprParser.Create_index_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#create_index_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_index_stmt(ExprParser.Create_index_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#indexed_column}.
	 * @param ctx the parse tree
	 */
	void enterIndexed_column(ExprParser.Indexed_columnContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#indexed_column}.
	 * @param ctx the parse tree
	 */
	void exitIndexed_column(ExprParser.Indexed_columnContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#create_table_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_table_stmt(ExprParser.Create_table_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#create_table_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_table_stmt(ExprParser.Create_table_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#column_def}.
	 * @param ctx the parse tree
	 */
	void enterColumn_def(ExprParser.Column_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#column_def}.
	 * @param ctx the parse tree
	 */
	void exitColumn_def(ExprParser.Column_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#type_name}.
	 * @param ctx the parse tree
	 */
	void enterType_name(ExprParser.Type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#type_name}.
	 * @param ctx the parse tree
	 */
	void exitType_name(ExprParser.Type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#column_constraint}.
	 * @param ctx the parse tree
	 */
	void enterColumn_constraint(ExprParser.Column_constraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#column_constraint}.
	 * @param ctx the parse tree
	 */
	void exitColumn_constraint(ExprParser.Column_constraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#signed_number}.
	 * @param ctx the parse tree
	 */
	void enterSigned_number(ExprParser.Signed_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#signed_number}.
	 * @param ctx the parse tree
	 */
	void exitSigned_number(ExprParser.Signed_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_constraint}.
	 * @param ctx the parse tree
	 */
	void enterTable_constraint(ExprParser.Table_constraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_constraint}.
	 * @param ctx the parse tree
	 */
	void exitTable_constraint(ExprParser.Table_constraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#foreign_key_clause}.
	 * @param ctx the parse tree
	 */
	void enterForeign_key_clause(ExprParser.Foreign_key_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#foreign_key_clause}.
	 * @param ctx the parse tree
	 */
	void exitForeign_key_clause(ExprParser.Foreign_key_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#conflict_clause}.
	 * @param ctx the parse tree
	 */
	void enterConflict_clause(ExprParser.Conflict_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#conflict_clause}.
	 * @param ctx the parse tree
	 */
	void exitConflict_clause(ExprParser.Conflict_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#create_trigger_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_trigger_stmt(ExprParser.Create_trigger_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#create_trigger_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_trigger_stmt(ExprParser.Create_trigger_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#create_view_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_view_stmt(ExprParser.Create_view_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#create_view_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_view_stmt(ExprParser.Create_view_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#create_virtual_table_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCreate_virtual_table_stmt(ExprParser.Create_virtual_table_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#create_virtual_table_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCreate_virtual_table_stmt(ExprParser.Create_virtual_table_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#with_clause}.
	 * @param ctx the parse tree
	 */
	void enterWith_clause(ExprParser.With_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#with_clause}.
	 * @param ctx the parse tree
	 */
	void exitWith_clause(ExprParser.With_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#cte_table_name}.
	 * @param ctx the parse tree
	 */
	void enterCte_table_name(ExprParser.Cte_table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#cte_table_name}.
	 * @param ctx the parse tree
	 */
	void exitCte_table_name(ExprParser.Cte_table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#recursive_cte}.
	 * @param ctx the parse tree
	 */
	void enterRecursive_cte(ExprParser.Recursive_cteContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#recursive_cte}.
	 * @param ctx the parse tree
	 */
	void exitRecursive_cte(ExprParser.Recursive_cteContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#common_table_expression}.
	 * @param ctx the parse tree
	 */
	void enterCommon_table_expression(ExprParser.Common_table_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#common_table_expression}.
	 * @param ctx the parse tree
	 */
	void exitCommon_table_expression(ExprParser.Common_table_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#delete_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDelete_stmt(ExprParser.Delete_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#delete_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDelete_stmt(ExprParser.Delete_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#delete_stmt_limited}.
	 * @param ctx the parse tree
	 */
	void enterDelete_stmt_limited(ExprParser.Delete_stmt_limitedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#delete_stmt_limited}.
	 * @param ctx the parse tree
	 */
	void exitDelete_stmt_limited(ExprParser.Delete_stmt_limitedContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#detach_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDetach_stmt(ExprParser.Detach_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#detach_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDetach_stmt(ExprParser.Detach_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#drop_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDrop_stmt(ExprParser.Drop_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#drop_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDrop_stmt(ExprParser.Drop_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(ExprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(ExprParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#raise_function}.
	 * @param ctx the parse tree
	 */
	void enterRaise_function(ExprParser.Raise_functionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#raise_function}.
	 * @param ctx the parse tree
	 */
	void exitRaise_function(ExprParser.Raise_functionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_value(ExprParser.Literal_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_value(ExprParser.Literal_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#value_row}.
	 * @param ctx the parse tree
	 */
	void enterValue_row(ExprParser.Value_rowContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#value_row}.
	 * @param ctx the parse tree
	 */
	void exitValue_row(ExprParser.Value_rowContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#values_clause}.
	 * @param ctx the parse tree
	 */
	void enterValues_clause(ExprParser.Values_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#values_clause}.
	 * @param ctx the parse tree
	 */
	void exitValues_clause(ExprParser.Values_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#insert_stmt}.
	 * @param ctx the parse tree
	 */
	void enterInsert_stmt(ExprParser.Insert_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#insert_stmt}.
	 * @param ctx the parse tree
	 */
	void exitInsert_stmt(ExprParser.Insert_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#returning_clause}.
	 * @param ctx the parse tree
	 */
	void enterReturning_clause(ExprParser.Returning_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#returning_clause}.
	 * @param ctx the parse tree
	 */
	void exitReturning_clause(ExprParser.Returning_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#upsert_clause}.
	 * @param ctx the parse tree
	 */
	void enterUpsert_clause(ExprParser.Upsert_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#upsert_clause}.
	 * @param ctx the parse tree
	 */
	void exitUpsert_clause(ExprParser.Upsert_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#pragma_stmt}.
	 * @param ctx the parse tree
	 */
	void enterPragma_stmt(ExprParser.Pragma_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#pragma_stmt}.
	 * @param ctx the parse tree
	 */
	void exitPragma_stmt(ExprParser.Pragma_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#pragma_value}.
	 * @param ctx the parse tree
	 */
	void enterPragma_value(ExprParser.Pragma_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#pragma_value}.
	 * @param ctx the parse tree
	 */
	void exitPragma_value(ExprParser.Pragma_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#reindex_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReindex_stmt(ExprParser.Reindex_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#reindex_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReindex_stmt(ExprParser.Reindex_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSelect_stmt(ExprParser.Select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSelect_stmt(ExprParser.Select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void enterJoin_clause(ExprParser.Join_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void exitJoin_clause(ExprParser.Join_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#select_core}.
	 * @param ctx the parse tree
	 */
	void enterSelect_core(ExprParser.Select_coreContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#select_core}.
	 * @param ctx the parse tree
	 */
	void exitSelect_core(ExprParser.Select_coreContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#factored_select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFactored_select_stmt(ExprParser.Factored_select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#factored_select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFactored_select_stmt(ExprParser.Factored_select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#simple_select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_select_stmt(ExprParser.Simple_select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#simple_select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_select_stmt(ExprParser.Simple_select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#compound_select_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCompound_select_stmt(ExprParser.Compound_select_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#compound_select_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCompound_select_stmt(ExprParser.Compound_select_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_or_subquery}.
	 * @param ctx the parse tree
	 */
	void enterTable_or_subquery(ExprParser.Table_or_subqueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_or_subquery}.
	 * @param ctx the parse tree
	 */
	void exitTable_or_subquery(ExprParser.Table_or_subqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#result_column}.
	 * @param ctx the parse tree
	 */
	void enterResult_column(ExprParser.Result_columnContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#result_column}.
	 * @param ctx the parse tree
	 */
	void exitResult_column(ExprParser.Result_columnContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#join_operator}.
	 * @param ctx the parse tree
	 */
	void enterJoin_operator(ExprParser.Join_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#join_operator}.
	 * @param ctx the parse tree
	 */
	void exitJoin_operator(ExprParser.Join_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#join_constraint}.
	 * @param ctx the parse tree
	 */
	void enterJoin_constraint(ExprParser.Join_constraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#join_constraint}.
	 * @param ctx the parse tree
	 */
	void exitJoin_constraint(ExprParser.Join_constraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#compound_operator}.
	 * @param ctx the parse tree
	 */
	void enterCompound_operator(ExprParser.Compound_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#compound_operator}.
	 * @param ctx the parse tree
	 */
	void exitCompound_operator(ExprParser.Compound_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#update_stmt}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_stmt(ExprParser.Update_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#update_stmt}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_stmt(ExprParser.Update_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#column_name_list}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name_list(ExprParser.Column_name_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#column_name_list}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name_list(ExprParser.Column_name_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#update_stmt_limited}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_stmt_limited(ExprParser.Update_stmt_limitedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#update_stmt_limited}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_stmt_limited(ExprParser.Update_stmt_limitedContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#qualified_table_name}.
	 * @param ctx the parse tree
	 */
	void enterQualified_table_name(ExprParser.Qualified_table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#qualified_table_name}.
	 * @param ctx the parse tree
	 */
	void exitQualified_table_name(ExprParser.Qualified_table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#vacuum_stmt}.
	 * @param ctx the parse tree
	 */
	void enterVacuum_stmt(ExprParser.Vacuum_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#vacuum_stmt}.
	 * @param ctx the parse tree
	 */
	void exitVacuum_stmt(ExprParser.Vacuum_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#filter_clause}.
	 * @param ctx the parse tree
	 */
	void enterFilter_clause(ExprParser.Filter_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#filter_clause}.
	 * @param ctx the parse tree
	 */
	void exitFilter_clause(ExprParser.Filter_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#window_defn}.
	 * @param ctx the parse tree
	 */
	void enterWindow_defn(ExprParser.Window_defnContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#window_defn}.
	 * @param ctx the parse tree
	 */
	void exitWindow_defn(ExprParser.Window_defnContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#over_clause}.
	 * @param ctx the parse tree
	 */
	void enterOver_clause(ExprParser.Over_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#over_clause}.
	 * @param ctx the parse tree
	 */
	void exitOver_clause(ExprParser.Over_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#frame_spec}.
	 * @param ctx the parse tree
	 */
	void enterFrame_spec(ExprParser.Frame_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#frame_spec}.
	 * @param ctx the parse tree
	 */
	void exitFrame_spec(ExprParser.Frame_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#frame_clause}.
	 * @param ctx the parse tree
	 */
	void enterFrame_clause(ExprParser.Frame_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#frame_clause}.
	 * @param ctx the parse tree
	 */
	void exitFrame_clause(ExprParser.Frame_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#simple_function_invocation}.
	 * @param ctx the parse tree
	 */
	void enterSimple_function_invocation(ExprParser.Simple_function_invocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#simple_function_invocation}.
	 * @param ctx the parse tree
	 */
	void exitSimple_function_invocation(ExprParser.Simple_function_invocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#aggregate_function_invocation}.
	 * @param ctx the parse tree
	 */
	void enterAggregate_function_invocation(ExprParser.Aggregate_function_invocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#aggregate_function_invocation}.
	 * @param ctx the parse tree
	 */
	void exitAggregate_function_invocation(ExprParser.Aggregate_function_invocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#window_function_invocation}.
	 * @param ctx the parse tree
	 */
	void enterWindow_function_invocation(ExprParser.Window_function_invocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#window_function_invocation}.
	 * @param ctx the parse tree
	 */
	void exitWindow_function_invocation(ExprParser.Window_function_invocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#common_table_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCommon_table_stmt(ExprParser.Common_table_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#common_table_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCommon_table_stmt(ExprParser.Common_table_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#order_by_stmt}.
	 * @param ctx the parse tree
	 */
	void enterOrder_by_stmt(ExprParser.Order_by_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#order_by_stmt}.
	 * @param ctx the parse tree
	 */
	void exitOrder_by_stmt(ExprParser.Order_by_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#limit_stmt}.
	 * @param ctx the parse tree
	 */
	void enterLimit_stmt(ExprParser.Limit_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#limit_stmt}.
	 * @param ctx the parse tree
	 */
	void exitLimit_stmt(ExprParser.Limit_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#ordering_term}.
	 * @param ctx the parse tree
	 */
	void enterOrdering_term(ExprParser.Ordering_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#ordering_term}.
	 * @param ctx the parse tree
	 */
	void exitOrdering_term(ExprParser.Ordering_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#asc_desc}.
	 * @param ctx the parse tree
	 */
	void enterAsc_desc(ExprParser.Asc_descContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#asc_desc}.
	 * @param ctx the parse tree
	 */
	void exitAsc_desc(ExprParser.Asc_descContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#frame_left}.
	 * @param ctx the parse tree
	 */
	void enterFrame_left(ExprParser.Frame_leftContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#frame_left}.
	 * @param ctx the parse tree
	 */
	void exitFrame_left(ExprParser.Frame_leftContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#frame_right}.
	 * @param ctx the parse tree
	 */
	void enterFrame_right(ExprParser.Frame_rightContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#frame_right}.
	 * @param ctx the parse tree
	 */
	void exitFrame_right(ExprParser.Frame_rightContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#frame_single}.
	 * @param ctx the parse tree
	 */
	void enterFrame_single(ExprParser.Frame_singleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#frame_single}.
	 * @param ctx the parse tree
	 */
	void exitFrame_single(ExprParser.Frame_singleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#window_function}.
	 * @param ctx the parse tree
	 */
	void enterWindow_function(ExprParser.Window_functionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#window_function}.
	 * @param ctx the parse tree
	 */
	void exitWindow_function(ExprParser.Window_functionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#offset}.
	 * @param ctx the parse tree
	 */
	void enterOffset(ExprParser.OffsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#offset}.
	 * @param ctx the parse tree
	 */
	void exitOffset(ExprParser.OffsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#default_value}.
	 * @param ctx the parse tree
	 */
	void enterDefault_value(ExprParser.Default_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#default_value}.
	 * @param ctx the parse tree
	 */
	void exitDefault_value(ExprParser.Default_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#partition_by}.
	 * @param ctx the parse tree
	 */
	void enterPartition_by(ExprParser.Partition_byContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#partition_by}.
	 * @param ctx the parse tree
	 */
	void exitPartition_by(ExprParser.Partition_byContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#order_by_expr}.
	 * @param ctx the parse tree
	 */
	void enterOrder_by_expr(ExprParser.Order_by_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#order_by_expr}.
	 * @param ctx the parse tree
	 */
	void exitOrder_by_expr(ExprParser.Order_by_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#order_by_expr_asc_desc}.
	 * @param ctx the parse tree
	 */
	void enterOrder_by_expr_asc_desc(ExprParser.Order_by_expr_asc_descContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#order_by_expr_asc_desc}.
	 * @param ctx the parse tree
	 */
	void exitOrder_by_expr_asc_desc(ExprParser.Order_by_expr_asc_descContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#expr_asc_desc}.
	 * @param ctx the parse tree
	 */
	void enterExpr_asc_desc(ExprParser.Expr_asc_descContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#expr_asc_desc}.
	 * @param ctx the parse tree
	 */
	void exitExpr_asc_desc(ExprParser.Expr_asc_descContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#initial_select}.
	 * @param ctx the parse tree
	 */
	void enterInitial_select(ExprParser.Initial_selectContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#initial_select}.
	 * @param ctx the parse tree
	 */
	void exitInitial_select(ExprParser.Initial_selectContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#recursive_select}.
	 * @param ctx the parse tree
	 */
	void enterRecursive_select(ExprParser.Recursive_selectContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#recursive_select}.
	 * @param ctx the parse tree
	 */
	void exitRecursive_select(ExprParser.Recursive_selectContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void enterUnary_operator(ExprParser.Unary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void exitUnary_operator(ExprParser.Unary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#error_message}.
	 * @param ctx the parse tree
	 */
	void enterError_message(ExprParser.Error_messageContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#error_message}.
	 * @param ctx the parse tree
	 */
	void exitError_message(ExprParser.Error_messageContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#module_argument}.
	 * @param ctx the parse tree
	 */
	void enterModule_argument(ExprParser.Module_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#module_argument}.
	 * @param ctx the parse tree
	 */
	void exitModule_argument(ExprParser.Module_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#column_alias}.
	 * @param ctx the parse tree
	 */
	void enterColumn_alias(ExprParser.Column_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#column_alias}.
	 * @param ctx the parse tree
	 */
	void exitColumn_alias(ExprParser.Column_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#keyword}.
	 * @param ctx the parse tree
	 */
	void enterKeyword(ExprParser.KeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#keyword}.
	 * @param ctx the parse tree
	 */
	void exitKeyword(ExprParser.KeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(ExprParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(ExprParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#function_name}.
	 * @param ctx the parse tree
	 */
	void enterFunction_name(ExprParser.Function_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#function_name}.
	 * @param ctx the parse tree
	 */
	void exitFunction_name(ExprParser.Function_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#schema_name}.
	 * @param ctx the parse tree
	 */
	void enterSchema_name(ExprParser.Schema_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#schema_name}.
	 * @param ctx the parse tree
	 */
	void exitSchema_name(ExprParser.Schema_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(ExprParser.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(ExprParser.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_or_index_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_or_index_name(ExprParser.Table_or_index_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_or_index_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_or_index_name(ExprParser.Table_or_index_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#column_name}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name(ExprParser.Column_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#column_name}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name(ExprParser.Column_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#collation_name}.
	 * @param ctx the parse tree
	 */
	void enterCollation_name(ExprParser.Collation_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#collation_name}.
	 * @param ctx the parse tree
	 */
	void exitCollation_name(ExprParser.Collation_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#foreign_table}.
	 * @param ctx the parse tree
	 */
	void enterForeign_table(ExprParser.Foreign_tableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#foreign_table}.
	 * @param ctx the parse tree
	 */
	void exitForeign_table(ExprParser.Foreign_tableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#index_name}.
	 * @param ctx the parse tree
	 */
	void enterIndex_name(ExprParser.Index_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#index_name}.
	 * @param ctx the parse tree
	 */
	void exitIndex_name(ExprParser.Index_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#trigger_name}.
	 * @param ctx the parse tree
	 */
	void enterTrigger_name(ExprParser.Trigger_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#trigger_name}.
	 * @param ctx the parse tree
	 */
	void exitTrigger_name(ExprParser.Trigger_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#view_name}.
	 * @param ctx the parse tree
	 */
	void enterView_name(ExprParser.View_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#view_name}.
	 * @param ctx the parse tree
	 */
	void exitView_name(ExprParser.View_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#module_name}.
	 * @param ctx the parse tree
	 */
	void enterModule_name(ExprParser.Module_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#module_name}.
	 * @param ctx the parse tree
	 */
	void exitModule_name(ExprParser.Module_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#pragma_name}.
	 * @param ctx the parse tree
	 */
	void enterPragma_name(ExprParser.Pragma_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#pragma_name}.
	 * @param ctx the parse tree
	 */
	void exitPragma_name(ExprParser.Pragma_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#savepoint_name}.
	 * @param ctx the parse tree
	 */
	void enterSavepoint_name(ExprParser.Savepoint_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#savepoint_name}.
	 * @param ctx the parse tree
	 */
	void exitSavepoint_name(ExprParser.Savepoint_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_alias}.
	 * @param ctx the parse tree
	 */
	void enterTable_alias(ExprParser.Table_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_alias}.
	 * @param ctx the parse tree
	 */
	void exitTable_alias(ExprParser.Table_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#transaction_name}.
	 * @param ctx the parse tree
	 */
	void enterTransaction_name(ExprParser.Transaction_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#transaction_name}.
	 * @param ctx the parse tree
	 */
	void exitTransaction_name(ExprParser.Transaction_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#window_name}.
	 * @param ctx the parse tree
	 */
	void enterWindow_name(ExprParser.Window_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#window_name}.
	 * @param ctx the parse tree
	 */
	void exitWindow_name(ExprParser.Window_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#alias}.
	 * @param ctx the parse tree
	 */
	void enterAlias(ExprParser.AliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#alias}.
	 * @param ctx the parse tree
	 */
	void exitAlias(ExprParser.AliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#filename}.
	 * @param ctx the parse tree
	 */
	void enterFilename(ExprParser.FilenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#filename}.
	 * @param ctx the parse tree
	 */
	void exitFilename(ExprParser.FilenameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#base_window_name}.
	 * @param ctx the parse tree
	 */
	void enterBase_window_name(ExprParser.Base_window_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#base_window_name}.
	 * @param ctx the parse tree
	 */
	void exitBase_window_name(ExprParser.Base_window_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#simple_func}.
	 * @param ctx the parse tree
	 */
	void enterSimple_func(ExprParser.Simple_funcContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#simple_func}.
	 * @param ctx the parse tree
	 */
	void exitSimple_func(ExprParser.Simple_funcContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#aggregate_func}.
	 * @param ctx the parse tree
	 */
	void enterAggregate_func(ExprParser.Aggregate_funcContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#aggregate_func}.
	 * @param ctx the parse tree
	 */
	void exitAggregate_func(ExprParser.Aggregate_funcContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#table_function_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_function_name(ExprParser.Table_function_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#table_function_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_function_name(ExprParser.Table_function_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#any_name}.
	 * @param ctx the parse tree
	 */
	void enterAny_name(ExprParser.Any_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#any_name}.
	 * @param ctx the parse tree
	 */
	void exitAny_name(ExprParser.Any_nameContext ctx);
}
