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

import IconButton from '@material-ui/core/IconButton';
import FirstPageIcon from '@material-ui/icons/FirstPage';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import LastPageIcon from '@material-ui/icons/LastPage';

import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import DescriptionIcon from '@material-ui/icons/Description';

import history from '../../../app/components/history';

function TablePaginationActions(props) {
  const { count, page, rowsPerPage, onChangePage } = props;

  const handleFirstPageButtonClick = (event) => {
    onChangePage(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onChangePage(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onChangePage(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onChangePage(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <div>
      <IconButton onClick={handleFirstPageButtonClick} disabled={page === 0} aria-label="first page" >
        <FirstPageIcon />
      </IconButton>
      <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="previous page" >
        <KeyboardArrowLeft />
      </IconButton>
      <IconButton onClick={handleNextButtonClick} disabled={page >= Math.ceil(count / rowsPerPage) - 1} aria-label="next page" >
        <KeyboardArrowRight />
      </IconButton>
      <IconButton onClick={handleLastPageButtonClick} disabled={page >= Math.ceil(count / rowsPerPage) - 1} aria-label="last page" >
        <LastPageIcon />
      </IconButton>
    </div>
  );
}

TablePaginationActions.propTypes = {
  count: PropTypes.number.isRequired,
  onChangePage: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
};

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
  footer: {
    padding: "14px 5px",
    fontSize: "14px"
  },
  spacer: {
    flex: '0 0 auto',
  }
});

export const ReactTable = ({
  columns,
  data,
  baseUrl,
  title,
  showAddButton,
  showViewButton,
  showEditButton,
  showDeleteButton,
}) => {
  const {
    getTableProps,
    //getTableBodyProps,
    headerGroups,
    footerGroups,
    rows,
    prepareRow,
    preGlobalFilteredRows,
    setGlobalFilter,
    //state,

    // refer to: https://codesandbox.io/s/react-table-global-filter-pagination-49w2p?file=/src/App.js:3907-3911
    page, // Instead of using 'rows', we'll use page,
    // which has only the rows for the active page

    // The rest of these things are super handy, too ;)
    //canPreviousPage,
    //canNextPage,
    //pageOptions,
    //pageCount,
    gotoPage,
    //nextPage,
    //previousPage,
    setPageSize,

    state: {
      pageIndex,
      pageSize,
      globalFilter
    },
  } = useTable(
    {
      columns,
      data
    },
    useGlobalFilter,
    useSortBy,
    usePagination,
  )

  React.useEffect(() => {
    // props.dispatch({ type: actions.resetPage })
    //console.log(globalFilter);
  }, [globalFilter]);

  const handleChangePage = (event, newPage) => {
    gotoPage(newPage)
  }

  const handleChangeRowsPerPage = event => {
    setPageSize(Number(event.target.value))
  }

  const handleView = row => {
    history.push(`${baseUrl}/${row.original.id}`);
  }

  const handleEdit = row => {
    history.push(`${baseUrl}/edit/${row.original.id}`);
  }

  const handleDelete = row => {
    history.push(`${baseUrl}/delete/${row.original.id}`);
  }

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows = pageIndex > 0 ? Math.max(0, (1 + pageIndex) * pageSize - rows.length) : 0;

  const classes = useStyles();

  // Render the UI for your table
  return (
    <TableContainer>
      <ReactTableToolbar
        title={title}
        preGlobalFilteredRows={preGlobalFilteredRows}
        setGlobalFilter={setGlobalFilter}
        globalFilter={globalFilter}
        baseUrl={baseUrl}
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
              <TableCell className={classes.headerCell} align="right"></TableCell>
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
                <TableCell className={classes.cell} align="right">
                  <div>
                      <IconButton aria-label="view" onClick={() => handleView(row)}>
                          <DescriptionIcon />
                      </IconButton>
                      <IconButton aria-label="edit" onClick={() => handleEdit(row)}>
                          <EditIcon />
                      </IconButton>
                      <IconButton aria-label="delete" onClick={() => handleDelete(row)}>
                          <DeleteIcon />
                      </IconButton>
                  </div>
                </TableCell>
              </TableRow>
            )
          })}

          {emptyRows > 0 && (
            <TableRow style={{ height: 47.2 * emptyRows }}>
              <TableCell />
            </TableRow>
          )}

        </TableBody>

        <TableFooter>
          {footerGroups.map(group => (
            <TableRow {...group.getFooterGroupProps()}>
                {group.headers.map(column => (
                <TableCell align='right' {...column.getFooterProps()} className={classes.footer}>{column.render('Footer')}</TableCell>
                ))}
                <TableCell> </TableCell>
            </TableRow>
          ))}            
          <TableRow>
            <TablePagination
              classes={{
                spacer: classes.spacer,
              }}
              rowsPerPageOptions={[5, 10, 25, { label: 'All', value: data.length }, ]}
              colSpan={columns.length + 1}
              count={rows.length}
              rowsPerPage={pageSize}
              page={pageIndex}
              SelectProps={{
                inputProps: { 'aria-label': 'rows per page' },
                native: true,
              }}
              onChangePage={handleChangePage}
              onChangeRowsPerPage={handleChangeRowsPerPage}
              ActionsComponent={TablePaginationActions}
              align="left"
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