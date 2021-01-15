import React from 'react'

import clsx from 'clsx'
import GlobalFilter from './GlobalFilter'
import IconButton from '@material-ui/core/IconButton'
import { lighten, makeStyles } from '@material-ui/core/styles'
import PropTypes from 'prop-types'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Tooltip from '@material-ui/core/Tooltip'
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';

import history from '../../../app/components/history';

const useToolbarStyles = makeStyles(theme => ({
  root: {
    padding: "0px",
    height: "32px",
    minHeight: "32px",
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
    color: "grey",
  },
}))

const ReactTableToolbar = props => {
  const classes = useToolbarStyles()
  const {
    numSelected,
    baseUrl,
    preGlobalFilteredRows,
    setGlobalFilter,
    globalFilter,
  } = props

  const handleAdd = () =>  {
    history.push(`${baseUrl}/new`);
  }

  return (
    <Toolbar
      className={clsx(classes.root, {
        [classes.highlight]: numSelected > 0,
      })}
    >
      <Typography className={classes.title} variant="h6" id="tableTitle">
        {props.title}
      </Typography>

      <GlobalFilter
        preGlobalFilteredRows={preGlobalFilteredRows}
        globalFilter={globalFilter}
        setGlobalFilter={setGlobalFilter}
      />

      <Tooltip title="Add">
        <IconButton aria-label="add" onClick={() => handleAdd()}>
          <AddCircleOutlineIcon />
        </IconButton>
      </Tooltip>

    </Toolbar>
  )
}

ReactTableToolbar.propTypes = {
  setGlobalFilter: PropTypes.func.isRequired,
  preGlobalFilteredRows: PropTypes.array.isRequired,
}

export default ReactTableToolbar