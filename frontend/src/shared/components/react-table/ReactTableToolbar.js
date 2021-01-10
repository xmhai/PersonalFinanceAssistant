import React from 'react'

import clsx from 'clsx'
import DeleteIcon from '@material-ui/icons/Delete'
import GlobalFilter from './GlobalFilter'
import IconButton from '@material-ui/core/IconButton'
import { lighten, makeStyles } from '@material-ui/core/styles'
import PropTypes from 'prop-types'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Tooltip from '@material-ui/core/Tooltip'
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';

const useToolbarStyles = makeStyles(theme => ({
  root: {
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(1),
  },
  highlight:
    theme.palette.type === 'light'
      ? {
          color: theme.palette.secondary.main,
          backgroundColor: lighten(theme.palette.secondary.light, 0.85),
        }
      : {
          color: theme.palette.text.primary,
          backgroundColor: theme.palette.secondary.dark,
        },
  title: {
    flex: '1 1 100%',
  },
}))

const ReactTableToolbar = props => {
  const classes = useToolbarStyles()
  const {
    numSelected,
    addUserHandler,
    deleteUserHandler,
    preGlobalFilteredRows,
    setGlobalFilter,
    globalFilter,
  } = props
  return (
    <Toolbar
      className={clsx(classes.root, {
        [classes.highlight]: numSelected > 0,
      })}
    >
      <Typography className={classes.title} variant="h6" id="tableTitle">
        Profit
      </Typography>

      <GlobalFilter
        preGlobalFilteredRows={preGlobalFilteredRows}
        globalFilter={globalFilter}
        setGlobalFilter={setGlobalFilter}
      />

      <Tooltip title="Add">
        <IconButton aria-label="add" onClick={addUserHandler}>
          <AddCircleOutlineIcon />
        </IconButton>
      </Tooltip>

    </Toolbar>
  )
}

ReactTableToolbar.propTypes = {
  setGlobalFilter: PropTypes.func.isRequired,
  preGlobalFilteredRows: PropTypes.array.isRequired,
  globalFilter: PropTypes.string.isRequired,
}

export default ReactTableToolbar