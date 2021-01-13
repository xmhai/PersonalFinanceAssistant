import React from 'react'

import { makeStyles } from '@material-ui/core/styles';
//import Checkbox from '@material-ui/core/Checkbox'
import PropTypes from 'prop-types'
import Table from '@material-ui/core/Table'
import TableBody from '@material-ui/core/TableBody'
import TableCell from '@material-ui/core/TableCell'
import TableContainer from '@material-ui/core/TableContainer'
import TableFooter from '@material-ui/core/TableFooter'
import TableHead from '@material-ui/core/TableHead'
import TablePagination from '@material-ui/core/TablePagination'
import TableRow from '@material-ui/core/TableRow'
import TableSortLabel from '@material-ui/core/TableSortLabel'
import {
  useGlobalFilter,
  usePagination,
  useSortBy,
  useTable,
} from 'react-table'

import ReactTableToolbar from './ReactTableToolbar'

const useStyles = makeStyles({
  table: {
    fontSize: "20px"
  },
  cell: {
    height: "10px",
    padding: "1px 5px",
    fontSize: "14px"
  },
  headerCell: {
    padding: "14px 5px",
    fontSize: "14px"
  },
  body: {
    minWidth: 650,
  },
  footer: {
    padding: "14px 5px",
    fontSize: "14px"
  },
});

export const ReactTable = ({
  title,
  columns,
  data,
  skipPageReset,
}) => {
  const {
    getTableProps,
    headerGroups,
    footerGroups,
    prepareRow,
    page,
    gotoPage,
    setPageSize,
    preGlobalFilteredRows,
    setGlobalFilter,
    state: { pageIndex, pageSize, globalFilter },
  } = useTable(
    {
      columns,
      data,
      autoResetPage: !skipPageReset,
    },
    useGlobalFilter,
    useSortBy,
    usePagination,
  )

  const handleChangePage = (event, newPage) => {
    gotoPage(newPage)
  }

  const handleChangeRowsPerPage = event => {
    setPageSize(Number(event.target.value))
  }

  const classes = useStyles();

  // Render the UI for your table
  return (
    <TableContainer>
      <ReactTableToolbar
        title={title}
        preGlobalFilteredRows={preGlobalFilteredRows}
        setGlobalFilter={setGlobalFilter}
        globalFilter={globalFilter}
      />        
      <Table {...getTableProps()} className={classes.table}>
        <TableHead>
          {headerGroups.map(headerGroup => (
            <TableRow {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                <TableCell className={classes.headerCell}
                  width={column.width}
                  align={column.type === 'numeric' ? 'right' : 'left'}
                  {...(column.id === 'selection' ? column.getHeaderProps() : column.getHeaderProps(column.getSortByToggleProps()))}
                >
                  {column.type === 'numeric' ?
                    (<TableSortLabel
                      active={column.isSorted}
                      // react-table has a unsorted state which is not treated here
                      direction={column.isSortedDesc ? 'desc' : 'asc'}
                    />) : null}
                  {column.render('Header')}
                  {column.type !== 'numeric' ?
                    (<TableSortLabel
                      active={column.isSorted}
                      // react-table has a unsorted state which is not treated here
                      direction={column.isSortedDesc ? 'desc' : 'asc'}
                    />) : null}
                </TableCell>
              ))}
            </TableRow>
          ))}
        </TableHead>
        <TableBody>
          {page.map((row, i) => {
            prepareRow(row)
            return (
              <TableRow {...row.getRowProps()}>
                {row.cells.map(cell => {
                  return (
                    <TableCell {...cell.getCellProps()} className={classes.cell}>
                      {cell.render('Cell')}
                    </TableCell>
                  )
                })}
              </TableRow>
            )
          })}
        </TableBody>

        <TableFooter>
          {footerGroups.map(group => (
            <TableRow {...group.getFooterGroupProps()}>
                {group.headers.map(column => (
                <TableCell align='right' {...column.getFooterProps()} className={classes.footer}>{column.render('Footer')}</TableCell>
                ))}
            </TableRow>
          ))}            
          <TableRow>
            <TablePagination
              rowsPerPageOptions={[
                5,
                10,
                25,
                { label: 'All', value: data.length },
              ]}
              colSpan={3}
              count={data.length}
              rowsPerPage={pageSize}
              page={pageIndex}
              SelectProps={{
                inputProps: { 'aria-label': 'rows per page' },
                native: true,
              }}
              onChangePage={handleChangePage}
              onChangeRowsPerPage={handleChangeRowsPerPage}
            />
          </TableRow>
        </TableFooter>
      </Table>
    </TableContainer>
  )
}

ReactTable.propTypes = {
  columns: PropTypes.array.isRequired,
  data: PropTypes.array.isRequired,
}

export default ReactTable